package br.com.bb.my_patisseries.config.errors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.bb.my_patisseries.model.dto.FormErrorDTO;

@RestControllerAdvice
public class ValidationHandlerErrors {
	
	@Autowired		
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDTO> handle(MethodArgumentNotValidException exception) {
		List<FormErrorDTO> errorsList = new ArrayList<FormErrorDTO>();
		List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();
		fieldErrorList.forEach(f-> {
			String message = messageSource.getMessage(f, LocaleContextHolder.getLocale());
			FormErrorDTO formError = new FormErrorDTO(f.getField(), message);
			errorsList.add(formError);
		});
		
		return errorsList;
	}

}
