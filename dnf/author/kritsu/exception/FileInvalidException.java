package dnf.author.kritsu.exception;

@SuppressWarnings("serial")
public class FileInvalidException extends RuntimeException{
    public FileInvalidException() {
    }
    public FileInvalidException(String message) {
        super(message);
    }
}