package br.com.produtos.apirest.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroValidacaoHandler {
    @Autowired
    private MessageSource messageSource;
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroValidacaoDTO> handle(MethodArgumentNotValidException ex){
        List<ErroValidacaoDTO> erros = new ArrayList<ErroValidacaoDTO>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {
            String mensagem = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            ErroValidacaoDTO dto = new ErroValidacaoDTO(err.getField(), mensagem);
            erros.add(dto);
        });
        return erros;
    }
}

class ErroValidacaoDTO{
    private String campo;
    private String erro;

    public ErroValidacaoDTO(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
