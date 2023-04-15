package br.com.mimobella.configs;

import br.com.mimobella.dtos.ObjetoErroDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

    private String msg = "";

    /*Captura excessões do projeto*/
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        if (ex instanceof MethodArgumentNotValidException){
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            for (ObjectError objectError : list) {
                msg += objectError.getDefaultMessage() + "\n";
            }
        }else{
            msg = ex.getMessage();
        }
        objetoErroDTO.setError(msg);
        objetoErroDTO.setCod(status.value() + " ==> " + status.getReasonPhrase());

        ex.printStackTrace();

        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*Caputura erro de banco*/
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex){

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        if (ex instanceof DataIntegrityViolationException){
            msg = "Erro de Integridade no banco " + ((DataIntegrityViolationException)ex).getCause().getMessage();
        }else if (ex instanceof ConstraintViolationException){
            msg = "Chave estrangeira violada + " + ((ConstraintViolationException)ex).getCause().getMessage();
        }else if (ex instanceof SQLException){
            msg = "Erro de banco de dados " +((SQLException)ex).getCause().getMessage();
        }else {
            msg = ex.getMessage();
        }

        objetoErroDTO.setError(msg);
        objetoErroDTO.setCod(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        ex.printStackTrace();

        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExcepetionJava.class)
    public ResponseEntity<Object> handleExceptionCustom(ExcepetionJava ex){
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
        objetoErroDTO.setError(ex.getMessage());
        objetoErroDTO.setCod(HttpStatus.OK.toString());

        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.OK);
    }

}
