package commands;

/**
 * An interface class for all commands
 */
public interface Command {

    /**
     * Executes the command
     *
     * @return command output
     */
    public String execute();

}
