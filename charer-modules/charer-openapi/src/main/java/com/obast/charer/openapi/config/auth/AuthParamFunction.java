package com.obast.charer.openapi.config.auth;

/**
 * 设定一个函数，并传入一个参数，方便在Lambda表达式下的函数式编程
 */
@FunctionalInterface
public interface AuthParamFunction<T> {
	
	/**
	 * 执行的方法 
	 * @param r 传入的参数 
	 */
	public void run(T r);
	
}
