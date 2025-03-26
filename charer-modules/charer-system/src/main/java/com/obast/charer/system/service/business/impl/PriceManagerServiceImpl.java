package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.model.dto.PricePeriod;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.system.dto.bo.PriceBo;
import com.obast.charer.system.dto.vo.price.PriceVo;
import com.obast.charer.system.operate.IChargerOperateService;
import com.obast.charer.system.service.business.IPriceManagerService;
import com.obast.charer.qo.PriceQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.price.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则管理服务实现
 */
@Service
public class PriceManagerServiceImpl implements IPriceManagerService {

    @Autowired
    private IPriceData priceInfoData;

    @Autowired
    private IChargerData chargerData;



    @Autowired
    private IChargerOperateService chargerOperateService;

    @Override
    public Paging<PriceVo> queryPageList(PageRequest<PriceQueryBo> pageRequest) {
        return fillData(priceInfoData.findPage(pageRequest));
    }

    @Override
    public List<PriceVo> queryList(PageRequest<PriceQueryBo> request) {
        return fillData(priceInfoData.findList(request.getData()));
    }

    @Override
    public PriceVo queryDetail(String priceId) {
        return fillData(priceInfoData.findById(priceId));
    }

    private Paging<PriceVo> fillData(Paging<Price> paging) {
        List<PriceVo> priceVos = new ArrayList<>();
        for(Price price: paging.getRows()) {
            priceVos.add(fillData(price));
        }
        Paging<PriceVo> newPageList = new Paging<>(paging.getTotal(), new ArrayList<>());
        newPageList.setRows(priceVos);
        return newPageList;
    }

    private List<PriceVo> fillData(List<Price> prices) {
        List<PriceVo> priceVos = new ArrayList<>();
        for(Price price: prices) {
            priceVos.add(fillData(price));
        }
        return priceVos;
    }

    private PriceVo fillData(Price price) {
        PriceVo vo = MapstructUtils.convert(price, PriceVo.class);
        if(vo == null) {
            return vo;
        }
        vo.setCurrentPrice(priceInfoData.calcCurrentPrice(price));
        vo.setMaxPrice(priceInfoData.calcMaxPrice(price));
        vo.setMinPrice(priceInfoData.calcMinPrice(price));
        return vo;
    }

    @Override
    public boolean addPrice(PriceBo bo) {
        Price entity = bo.to(Price.class);
        //名称不可重复
        Price priceRepetition = priceInfoData.findByName(entity.getName());
        if (priceRepetition != null) {
            throw new BizException(ErrCode.PRICE_NAME_ALREADY);
        }
        validatePrice(bo);
        return priceInfoData.add(entity) != null;
    }

    @Override
    public boolean updatePrice(PriceBo bo) {
        Price di = bo.to(Price.class);
        //名称不可重复
        Price repetition = priceInfoData.findByName(di.getName());
        if (repetition != null && !repetition.getId().equals(di.getId())) {
            throw new BizException(ErrCode.PRICE_NAME_ALREADY);
        }

        validatePrice(bo);
        return priceInfoData.update(di) != null;
    }

    /**
     * 下发计费规则
     */
    @Override
    public boolean issuePrice(String priceId) {
        Price price = priceInfoData.findById(priceId);
        if(price == null) {
            throw new BizException(ErrCode.PRICE_NOT_FOUND);
        }

        List<Charger> chargers = chargerData.findByPriceId(price.getId());
        for(Charger charger: chargers) {
            chargerOperateService.priceConfig(charger.getId(), price);
        }
        return true;
    }

    @Override
    public void updateStatus(PriceBo bo) {
        Price price = priceInfoData.findById(bo.getId());
        price.setStatus(bo.getStatus());
        priceInfoData.save(price);
    }

    @Override
    @Transactional
    public boolean deletePrice(String priceId) {
        priceId = queryDetail(priceId).getId();
        //查看是否存在设备
        List<Charger> chargers = chargerData.findByPriceId(priceId);
        if (!chargers.isEmpty()) {
            throw new BizException(ErrCode.PRICE_EXISTS_CHARGER);
        }
        priceInfoData.deleteById(priceId);
        return true;
    }

    @Override
    @Transactional
    public boolean batchDeletePrice(List<String> ids) {
        for(String priceId: ids) {
            deletePrice(priceId);
        }
        return true;
    }

    public void validatePrice(PriceBo bo) {
        if(bo.getType() == null) {
            throw new BizException(ErrCode.PRICE_VALIDATE_TYPE_EMPTY);
        }

        PriceProperties properties = bo.getProperties();
        if(properties == null) {
            throw new BizException(ErrCode.PRICE_VALIDATE_PROPERTIES_EMPTY);
        }

        List<PriceFee> priceFees = properties.getFee();
        if(priceFees == null || priceFees.isEmpty()) {
            throw new BizException(ErrCode.PRICE_VALIDATE_PROPERTIES_FEE_EMPTY);
        }

        if(bo.getType().equals(PriceTypeEnum.Standard)) {
            PriceFee priceFee = priceFees.get(0);
            if(priceFee == null) {
                throw new BizException(ErrCode.PRICE_STANDARD_PROPERTIES_FEE_EMPTY);
            }
            if(priceFee.getElecFee() == null || priceFee.getElecFee().compareTo(new BigDecimal(0)) == 0) {
                throw new BizException(ErrCode.PRICE_STANDARD_PROPERTIES_ELEC_FEE_EMPTY);
            }
            if(priceFee.getServiceFee() == null || priceFee.getServiceFee().compareTo(new BigDecimal(0)) == 0) {
                throw new BizException(ErrCode.PRICE_STANDARD_PROPERTIES_SERVICE_FEE_EMPTY);
            }
            priceFee.setAmount(priceFee.getElecFee().add(priceFee.getServiceFee()));

            properties.setPeriod(new ArrayList<>());
        } else {

            if(priceFees.size() != 4) {
                throw new BizException(ErrCode.PRICE_TOU_PROPERTIES_FEE_QTY_INVALID);
            }
            for(PriceFee priceFee: priceFees) {
                if(priceFee.getElecFee() == null || priceFee.getElecFee().compareTo(new BigDecimal(0)) == 0) {
                    throw new BizException(ErrCode.PRICE_TOU_PROPERTIES_ELEC_FEE_EMPTY);
                }

                if(priceFee.getServiceFee() == null || priceFee.getServiceFee().compareTo(new BigDecimal(0)) == 0) {
                    throw new BizException(ErrCode.PRICE_TOU_PROPERTIES_SERVICE_FEE_EMPTY);
                }
                priceFee.setAmount(priceFee.getElecFee().add(priceFee.getServiceFee()));
            }

            List<PricePeriod> pricePeriods = properties.getPeriod();
            if(pricePeriods == null || pricePeriods.isEmpty()) {
                throw new BizException(ErrCode.PRICE_TOU_PROPERTIES_PERIOD_EMPTY);
            }

            if(pricePeriods.size() != 48) {
                throw new BizException(ErrCode.PRICE_TOU_PROPERTIES_PERIOD_QTY_INVALID);
            }

            for(PricePeriod pricePeriod: pricePeriods) {
                if(StringUtils.isBlank(pricePeriod.getLabel())) {
                    throw new BizException(ErrCode.PRICE_TOU_PROPERTIES_PERIOD_LABEL_EMPTY);
                }

                if(pricePeriod.getIndex() == null) {
                    throw new BizException(ErrCode.PRICE_TOU_PROPERTIES_PERIOD_INDEX_EMPTY);
                }
            }
        }
    }
}