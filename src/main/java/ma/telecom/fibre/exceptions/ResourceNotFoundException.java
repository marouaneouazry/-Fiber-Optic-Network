package ma.telecom.fibre.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String id) {
        super(resource + " non trouvé avec l'id : " + id);
    }
}