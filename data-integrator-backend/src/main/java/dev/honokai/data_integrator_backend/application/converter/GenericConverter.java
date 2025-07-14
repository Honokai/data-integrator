package dev.honokai.data_integrator_backend.application.converter;

import java.lang.reflect.Field;

public class GenericConverter {
	public static <Source, Dest> Dest mapTo(Source source, Class<Dest> className) {
		try {
			Dest destination = className.getDeclaredConstructor().newInstance();

			for (Field sourceField : source.getClass().getDeclaredFields()) {
				Field destField = className.getDeclaredField(sourceField.getName());

				destField.setAccessible(true);
				sourceField.setAccessible(true);

				destField.set(destination, sourceField.get(source));
			}

			return destination;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

			throw new RuntimeException(String.format("Não foi possível mapear de %s para %s",
					source.getClass().getSimpleName(), className.getSimpleName()));
		}
	}
}
