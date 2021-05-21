package utn.isi.dan.pedido.exception;

public class NotFoundException extends RuntimeException{

    private static final String DESCRIPCION = "Not Found Exception";

    public NotFoundException(String detalle) {
        super(DESCRIPCION + ". " + detalle);
    }
    
	private static final long serialVersionUID = 1L;

}
