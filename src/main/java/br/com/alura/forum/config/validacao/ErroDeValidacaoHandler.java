package br.com.alura.forum.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

//classe atua como um interceptador, pois sempre que ocorrer a exception declarada abaixo, ele será chamado

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST) //diz qual status http será devolvido
    @ExceptionHandler(MethodArgumentNotValidException.class) //se acontecer esta exception, ele vai executar este método
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
        List<ErroDeFormularioDto> dto = new ArrayList<>();
        //armazena os campos que estão com erro de validação
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            //armazena a mensagem no idioma no qual o usuário possui
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
            dto.add(erro);
        });

        return dto;
    }
}
