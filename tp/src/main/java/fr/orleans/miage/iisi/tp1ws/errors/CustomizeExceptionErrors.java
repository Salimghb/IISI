package fr.orleans.miage.iisi.tp1ws.errors;

import exceptions.MaxNbCoupsException;
import exceptions.MotInexistantException;
import exceptions.PseudoDejaPrisException;
import exceptions.PseudoNonConnecteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomizeExceptionErrors extends ResponseEntityExceptionHandler {


    private static final String PASCONNECTE = "Vous n'êtes pas connecté !";
    private static final String PSEUDODEJAPRIS = "Le pseudo est déjà utilisé !";
    private static final String MOTINEXISTANT = "Mot inexistant !";
    private static final String LIMITEMAXCOUPS = "Vous avez atteint la limite maximum de coups autorisés !";



    @ExceptionHandler(PseudoDejaPrisException.class)
    public final ResponseEntity<ErrorsDetails> handlePseudosDejaPris(PseudoDejaPrisException ex, WebRequest request){
        ErrorsDetails errorsDetails = new ErrorsDetails(PSEUDODEJAPRIS, request.getDescription(false));
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(errorsDetails);
    }

    @ExceptionHandler(PseudoNonConnecteException.class)
    public final ResponseEntity<ErrorsDetails> handlePseudoNonConnecte(PseudoNonConnecteException ex, WebRequest request){
        ErrorsDetails errorsDetails = new ErrorsDetails(PASCONNECTE, request.getDescription(false));
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorsDetails);
    }


    @ExceptionHandler(MotInexistantException.class)
    public final ResponseEntity<ErrorsDetails> handleMotInexistantException(MotInexistantException ex, WebRequest request){
        ErrorsDetails errorsDetails = new ErrorsDetails(MOTINEXISTANT, request.getDescription(false));
        return  ResponseEntity.status(HttpStatus.GONE).body(errorsDetails);
    }

    @ExceptionHandler(MaxNbCoupsException.class)
    public final ResponseEntity<ErrorsDetails> handleMaxNbCoupsException(MaxNbCoupsException ex, WebRequest request){
        ErrorsDetails errorsDetails = new ErrorsDetails(LIMITEMAXCOUPS, request.getDescription(false));
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsDetails);
    }
}
