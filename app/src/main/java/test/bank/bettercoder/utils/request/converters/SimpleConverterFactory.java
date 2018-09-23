package test.bank.bettercoder.utils.request.converters;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.bank.bettercoder.utils.request.converters.json.JsonRequestBodyConverter;
import test.bank.bettercoder.utils.request.converters.json.JsonResponseBodyConverter;

public class SimpleConverterFactory extends Converter.Factory {

    public static SimpleConverterFactory create() {
        return new SimpleConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == JSONObject.class) {
            return new JsonResponseBodyConverter<JSONObject>();
        }

        return GsonConverterFactory.create().responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type == JSONObject.class) {
            return new JsonRequestBodyConverter<JSONObject>();
        }
        return GsonConverterFactory.create().requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
