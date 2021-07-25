package utn.isi.dan.pedido.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import utn.isi.dan.pedido.exception.BadRequestException;
import utn.isi.dan.pedido.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ NotFoundException.class})
    @ResponseBody
    public ErrorMessage notFound(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }   
    
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ BadRequestException.class})
    @ResponseBody
    public ErrorMessage badRequest(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }   


    


}
