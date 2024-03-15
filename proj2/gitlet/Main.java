package gitlet;

import static gitlet.Utils.exitPrintMessage;
/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author XiaoChangXuan
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        exitPrintMessage(args == null || args[0] == null, "Please enter a command.");
        if (args != null) {
            switch (args[0]) {
                case "init":
                    validateNumArgs(args, 1);
                    Repository.gitletInitial();
                    break;
                case "add":
                    validateNumArgs(args, 2);
                    Repository.gitletAddFile(args[1]);
                    break;
                case "commit":
                    exitPrintMessage(args.length == 1 || args.length == 2 && args[1].isEmpty(),
                            "Please enter a commit message.");
                    validateNumArgs(args, 2);
                    Repository.gitletCommit(args[1], null);
                    break;
                case "rm":
                    validateNumArgs(args, 2);
                    Repository.gitletRemoveFile(args[1]);
                    break;
                case "log":
                    validateNumArgs(args, 1);
                    Repository.gitletLog();
                    break;
                case "global-log":
                    validateNumArgs(args, 1);
                    Repository.gitletGlobalLog();
                    break;
                case "find":
                    validateNumArgs(args, 2);
                    Repository.gitletFindMessage(args[1]);
                    break;
                case "status":
                    validateNumArgs(args, 1);
                    Repository.gitletStatus();
                    break;
                case "branch":
                    validateNumArgs(args, 2);
                    Repository.gitletCreateBranch(args[1]);
                    break;
                case "rm-branch":
                    validateNumArgs(args, 2);
                    Repository.gitletDeleteBranch(args[1]);
                    break;
                case "checkout":
                    if (args.length == 3 && "--".equals(args[1])) {
                        Repository.gitletCheckoutFile(args[2]);
                    } else if (args.length == 4 && "--".equals(args[2])) {
                        Repository.gitletCheckoutCommitFile(args[1], args[3]);
                    } else if (args.length == 2) {
                        Repository.gitletCheckoutBranch(args[1]);
                    } else {
                        exitPrintMessage(true, "Incorrect operands.");
                    }
                    break;
                case "reset":
                    validateNumArgs(args, 2);
                    Repository.gitletReset(args[1]);
                    break;
                case "merge":
                    validateNumArgs(args, 2);
                    Repository.gitletMerge(args[1]);
                    break;
                default:
                    exitPrintMessage(true, "No command with that name exists.");
            }
        }
    }
    public static void validateNumArgs(String[] args, int n) {
        exitPrintMessage(args.length != n, "Incorrect operands.");
    }
}


