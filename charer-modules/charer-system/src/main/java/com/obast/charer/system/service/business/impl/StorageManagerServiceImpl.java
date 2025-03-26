package com.obast.charer.system.service.business.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.yitter.idgen.YitIdHelper;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.system.IStorageData;
import com.obast.charer.data.business.IPriceData;
import com.obast.charer.data.business.IStationData;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.data.platform.IProtocolData;
import com.obast.charer.model.Storage;
import com.obast.charer.model.product.Product;
import com.obast.charer.model.protocol.Protocol;
import com.obast.charer.qo.StorageQueryBo;
import com.obast.charer.system.dto.bo.ChargerBo;
import com.obast.charer.system.dto.bo.StorageBatchBo;
import com.obast.charer.system.dto.bo.StorageBo;
import com.obast.charer.system.dto.bo.StorageStoreBo;
import com.obast.charer.system.dto.vo.StorageVo;
import com.obast.charer.system.service.business.IChargerManagerService;
import com.obast.charer.system.service.business.IStorageManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电桩库管理服务实现
 */
@Service
public class StorageManagerServiceImpl implements IStorageManagerService {

    @Autowired
    private IStorageData chargerStorageData;

    @Autowired
    private IProductData productData;

    @Autowired
    private IProtocolData protocolData;

    @Autowired
    private IChargerManagerService chargerManagerService;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IPriceData priceData;

    @Override
    public Paging<StorageVo> queryPageList(PageRequest<StorageQueryBo> pageRequest) {
        Paging<Storage> pageList = chargerStorageData.findPage(pageRequest);
        Paging<StorageVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(Storage chargerGun: pageList.getRows()) {
            newPageList.getRows().add(fillData(chargerGun));
        }
        return newPageList;
    }

    @Override
    public List<StorageVo> queryList(PageRequest<StorageQueryBo> pageRequest) {
        List<Storage> list = chargerStorageData.findList(pageRequest.getData());
        List<StorageVo> chargerGunVos = new ArrayList<>();
        for(Storage chargerGun: list) {
            chargerGunVos.add(fillData(chargerGun));
        }
        return chargerGunVos;
    }

    @Override
    public StorageVo queryDetail(String id) {
        Storage charger = chargerStorageData.findById(id);
        return fillData(charger);
    }

    private StorageVo fillData(Storage chargerGun) {
        return MapstructUtils.convert(chargerGun, StorageVo.class);
    }

    @Override
    @Transactional
    public void add(StorageBo bo) {
        Storage chargerGun = bo.to(Storage.class);
        chargerStorageData.add(chargerGun);
    }

    @Override
    @Transactional
    public void batchAdd(StorageBatchBo bo) {

        Product product = productData.findByProductKey(bo.getProductKey());
        if(product == null) {
            throw new BizException(ErrCode.PRODUCT_NOT_FOUND);
        }

        Protocol protocol = protocolData.findByProtocolKey(product.getProtocolKey());
        if(protocol == null) {
            throw new BizException(ErrCode.PROTOCOL_NOT_FOUND);
        }

        Integer dnLength = protocol.getCharger().getDnLength();

        if(dnLength == null || dnLength <= 0 || dnLength >= 32) {
            throw new BizException(ErrCode.PROTOCOL_DN_LENGTH_INVALID);
        }

        Integer qty = bo.getQty();
        if(qty == null || qty <= 0 || qty > 99) {
            throw new BizException(ErrCode.CHARGER_STORAGE_QTY_INVALID);
        }

        String nameFormat = bo.getNameFormat();
        if(StringUtils.isBlank(nameFormat)) {
            nameFormat = "桩号";
        }

        for(int i = 1; i<=qty; i++) {
            String no = String.valueOf(YitIdHelper.nextId());
            String dn = no.substring(no.length()-dnLength);

            Storage chargerStorage = new Storage();
            chargerStorage.setProductKey(product.getProductKey());
            chargerStorage.setDn(dn);
            String formatted = nameFormat + new DecimalFormat("00").format(i);
            chargerStorage.setName(formatted);
            chargerStorageData.add(chargerStorage);
        }
    }

    @Override
    public void update(StorageBo bo) {
        Storage di = bo.to(Storage.class);
        chargerStorageData.update(di);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        chargerStorageData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String id: ids) {
            chargerStorageData.deleteById(id);
        }
        return true;
    }


    @Override
    @Transactional
    public boolean batchStore(StorageStoreBo bo) {
        List<String> chargerStorageIds = bo.getIds();
        if(ObjectUtil.isEmpty(chargerStorageIds)) {
            throw new BizException(ErrCode.CHARGER_STORAGE_IDS_EMPTY);
        }

        for(String chargerStorageId: chargerStorageIds) {
            Storage chargerStorage = chargerStorageData.findById(chargerStorageId);
            if(chargerStorage == null) {
                throw new BizException(ErrCode.CHARGER_STORAGE_NOT_FOUND);
            }

            ChargerBo chargerBo = new ChargerBo();
            chargerBo.setProductKey(chargerStorage.getProductKey());
            chargerBo.setName(chargerStorage.getName());
            chargerBo.setDn(chargerStorage.getDn());
            chargerBo.setStationId(bo.getStationId());
            chargerBo.setPriceId(bo.getPriceId());
            chargerBo.setStatus(bo.getStatus());

            chargerManagerService.addCharger(chargerBo);

            chargerStorageData.deleteById(chargerStorage.getId());
        }
        return true;
    }
}
