package kr.ldcc.internwork.model.mapper;

import org.modelmapper.ModelMapper;

public class ObjectMapperUtils {
    private static ModelMapper modelMapper = new ModelMapper();

    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }
}
