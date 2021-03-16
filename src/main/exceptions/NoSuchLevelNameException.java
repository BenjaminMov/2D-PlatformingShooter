package exceptions;

//Exception class for wanting to fetch a level with a name that can not be found
public class NoSuchLevelNameException extends Exception {

    public NoSuchLevelNameException(String msg) {
        super(msg);
    }

}
