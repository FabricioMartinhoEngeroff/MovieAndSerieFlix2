package com.DvFabricio.movieFlix2.service;

public interface IConvertData {

   <T> T getData(String json, Class<T> classe);
}
