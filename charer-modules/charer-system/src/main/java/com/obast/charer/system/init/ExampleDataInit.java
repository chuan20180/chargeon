//package cc.obast.system.service;
//
//import com.obast.charer.utils.common.JsonUtils;
//import com.obast.charer.utils.common.SpringUtils;
//import data.obast.com.ICommonData;
//import cc.obast.data.system.*;
//import cc.obast.data.system.*;
//import com.obast.charer.model.Id;
//import com.obast.charer.model.OauthClient;

//import com.obast.charer.model.device.Device;
//import com.obast.charer.model.device.DeviceGroup;
//import cc.obast.model.notify.Channel;
//import com.obast.charer.model.notify.ChannelConfig;
//import com.obast.charer.model.notify.NotifyTemplate;
//import com.obast.charer.model.notify.NotifyMessage;
//import com.obast.charer.model.plugin.PluginInfo;
//import com.obast.charer.model.product.Category;
//import com.obast.charer.model.product.Product;
//import com.obast.charer.model.product.ProductModel;
//import com.obast.charer.model.product.ThingModel;
//import com.obast.charer.model.rule.RuleInfo;
//import com.obast.charer.model.rule.TaskInfo;
//
//import cc.obast.model.system.*;
//import com.obast.charer.temporal.IDbStructureData;
//import com.fasterxml.jackson.core.type.TypeReference;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.SmartInitializingSingleton;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//@Slf4j
//@Service
//public class ExampleDataInit implements SmartInitializingSingleton {
//
//    @Value("${init.data.flag:true}")
//    private boolean initDataFlg;
//
//    @Value("${init.data.path:.}")
//    private String initDataPath;
//
//    @Autowired
//    private IDbStructureData dbStructureData;
//
//    @Override
//    public void afterSingletonsInstantiated() {
//        //等redis实例化后再执行
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    File initFile = new File(".init");
//                    if (initFile.exists()) {
//                        return;
//                    }
//
//                    if (!initDataFlg) {
//                        return;
//                    }
//                    initData("category", SpringUtils.getBean(ICategoryData.class), new TypeReference<List<Category>>() {
//                    });
//                    initData("deviceGroup", SpringUtils.getBean(IDeviceGroupData.class), new TypeReference<List<DeviceGroup>>() {
//                    });
//                    initData("deviceInfo", SpringUtils.getBean(IDeviceData.class), new TypeReference<List<Device>>() {
//                    });
//
//                    initData("oauthClient", SpringUtils.getBean(IOauthClientData.class), new TypeReference<List<OauthClient>>() {
//                    });
//                    initData("product", SpringUtils.getBean(IProductData.class), new TypeReference<List<Product>>() {
//                    });
//                    initData("productModel", SpringUtils.getBean(IProductModelData.class), new TypeReference<List<ProductModel>>() {
//                    });
//                    initData("ruleInfo", SpringUtils.getBean(IRuleInfoData.class), new TypeReference<List<RuleInfo>>() {
//                    });
//
//
//                    initData("taskInfo", SpringUtils.getBean(ITaskInfoData.class), new TypeReference<List<TaskInfo>>() {
//                    });
//                    List<ThingModel> thingModels = initData("thingModel", SpringUtils.getBean(IThingModelData.class), new TypeReference<>() {
//                    });
//                    //初始化物模型时序数据结构
//                    for (ThingModel thingModel : thingModels) {
//                        dbStructureData.defineThingModel(thingModel);
//                    }
//

//                    initData("channel", SpringUtils.getBean(IChannelData.class), new TypeReference<List<Channel>>() {
//                    });
//                    initData("channelConfig", SpringUtils.getBean(IChannelConfigData.class), new TypeReference<List<ChannelConfig>>() {
//                    });
//                    initData("channelTemplate", SpringUtils.getBean(INotifyTemplateData.class), new TypeReference<List<NotifyTemplate>>() {
//                    });
//                    initData("notifyMessage", SpringUtils.getBean(INotifyMessageData.class), new TypeReference<List<NotifyMessage>>() {
//                    });
//                    initData("pluginInfo", SpringUtils.getBean(IPluginInfoData.class), new TypeReference<List<PluginInfo>>() {
//                    });
//
//                    initSysData();
//
//                    log.info("init data finished.");
//
//                    FileUtils.write(initFile, "", StandardCharsets.UTF_8);
//                } catch (
//                        Exception e) {
//                    log.error("init error", e);
//                }
//            }
//        }, 100);
//
//    }
//
//    private void initSysData() {
//        initData("sys_config", SpringUtils.getBean(ISysConfigData.class), new TypeReference<List<SysConfig>>() {
//        });
//
//        initData("sys_dept", SpringUtils.getBean(ISysDeptData.class), new TypeReference<List<SysDept>>() {
//        });
//
//        initData("sys_dict_data", SpringUtils.getBean(ISysDictData.class), new TypeReference<List<SysDictData>>() {
//        });
//
//        initData("sys_dict_type", SpringUtils.getBean(ISysDictTypeData.class), new TypeReference<List<SysDictType>>() {
//        });
//
//        initData("sys_logininfor", SpringUtils.getBean(ISysLoginInfoData.class), new TypeReference<List<SysLoginInfo>>() {
//        });
//        initData("sys_menu", SpringUtils.getBean(ISysMenuData.class), new TypeReference<List<SysMenu>>() {
//        });
//
//        initData("sys_notice", SpringUtils.getBean(INoticeData.class), new TypeReference<List<Notice>>() {
//        });
//
//        initData("sys_oper_log", SpringUtils.getBean(ISysOperLogData.class), new TypeReference<List<SysOperLog>>() {
//        });
//
//        initData("sys_oss", SpringUtils.getBean(ISysOssData.class), new TypeReference<List<SysOss>>() {
//        });
//
//        initData("sys_oss_config", SpringUtils.getBean(ISysOssConfigData.class), new TypeReference<List<SysOssConfig>>() {
//        });
//
//        initData("sys_post", SpringUtils.getBean(ISysPostData.class), new TypeReference<List<SysPost>>() {
//        });
//        initData("sys_role", SpringUtils.getBean(ISysRoleData.class), new TypeReference<List<SysRole>>() {
//        });
//
//
//        initData("sys_role_menu", SpringUtils.getBean(ISysRoleMenuData.class), new TypeReference<List<SysRoleMenu>>() {
//        });
//
//        initData("sys_tenant", SpringUtils.getBean(ISysTenantData.class), new TypeReference<List<SysTenant>>() {
//        });
//
//        initData("sys_tenant_package", SpringUtils.getBean(ISysTenantPackageData.class), new TypeReference<List<SysTenantPackage>>() {
//        });
//
//        initData("sys_user", SpringUtils.getBean(ISysUserData.class), new TypeReference<List<SysUser>>() {
//        });
//
//        initData("sys_user_post", SpringUtils.getBean(ISysUserPostData.class), new TypeReference<List<SysUserPost>>() {
//        });
//
//        initData("sys_user_role", SpringUtils.getBean(ISysUserRoleData.class), new TypeReference<List<SysUserRole>>() {
//        });
//
//        initData("sys_app", SpringUtils.getBean(ISysAppData.class), new TypeReference<List<SysApp>>() {
//        });
//    }
//
//    private <T> T initData(String name, ICommonData service, TypeReference<T> type) {
//        try {
//            log.info("init {} data...", name);
//            if (service.count() > 0) {
//                log.error("原数据库已存在" + name + "的旧数据，请清除后再重新初始化！系统正在退出。。。");
//                System.exit(0);
//            }
//            String path = initDataPath;
//            if (initDataPath.equals(".")) {
//                path = "./data/init";
//            }
//            String json = FileUtils.readFileToString(Paths.get(path, name + ".json").toFile(), StandardCharsets.UTF_8);
//            List list = (List) JsonUtils.parseObject(json, type);
//            for (Object obj : list) {
//                service.save((Id) obj);
//            }
//            return (T) list;
//        } catch (Exception e) {
//            log.error("initData error", e);
//            return null;
//        }
//    }
//
//}
