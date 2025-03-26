package com.gitee.starblues.core.classloader;

import com.gitee.starblues.core.descriptor.InsidePluginDescriptor;
import com.gitee.starblues.loader.classloader.GenericClassLoader;
import com.gitee.starblues.loader.classloader.resource.loader.ResourceLoaderFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

/**
 * 插件 classLoader
 *
 * @author starBlues
 * @version 3.0.3
 * @since 3.0.0
 */
@Slf4j
public class PluginClassLoader extends GenericClassLoader implements PluginResourceLoaderFactory{

    private final MainResourceMatcher mainResourceMatcher;

    private final PluginResourceLoaderFactory proxy;

    public PluginClassLoader(String name, GenericClassLoader parentClassLoader,
                             ClassLoader classLoader,
                             ResourceLoaderFactory resourceLoaderFactory,
                             MainResourceMatcher mainResourceMatcher) {
        super(name, classLoader, resourceLoaderFactory);
        this.mainResourceMatcher = mainResourceMatcher;
        this.proxy = new PluginResourceLoaderFactoryProxy(resourceLoaderFactory, parentClassLoader);
    }

    @Override
    public void addResource(InsidePluginDescriptor descriptor) throws Exception {
        proxy.addResource(descriptor);
    }

    @Override
    protected Class<?> findClassFromParent(String className) throws ClassNotFoundException {
        if(mainResourceMatcher.match(className.replace(".", "/"))){
            try {
                return super.findClassFromParent(className);
            } catch (Exception e){
                // 忽略
            }
        }
        return null;
    }

    @Override
    protected InputStream findInputStreamFromParent(String name) {
        if(mainResourceMatcher.match(name)){
            try {
                return super.findInputStreamFromParent(name);
            } catch (Exception e){
                // 忽略
            }
        }
        return null;
    }

    @Override
    protected URL findResourceFromParent(String name) {
        if(mainResourceMatcher.match(name)){
            return super.findResourceFromParent(name);
        }
        return null;
    }

    @Override
    protected Enumeration<URL> findResourcesFromParent(String name) throws IOException {
        if(mainResourceMatcher.match(name)){
            return super.findResourcesFromParent(name);
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        super.close();
        if(mainResourceMatcher instanceof AutoCloseable){
            try {
                ((AutoCloseable) mainResourceMatcher).close();
            } catch (Exception e){
                throw new IOException(e);
            }
        }
    }
}
