package gitlet;

import static gitlet.Utils.myException;
/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author XiaoChangXuan
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        myException(args == null || args.length == 0, "Please enter a command.");
        String fileName, message, branchName, commitId;
        switch (args[0]) {
            case "init":
                validateNumArgs(args, 1);
                Repository.gitletInitial();
                break;
            case "add":
                validateNumArgs(args, 2);
                fileName = args[1];
                Repository.gitletAddFile(fileName);
                break;
            case "commit":
                myException(args.length == 1, "Please enter a commit message.");
                validateNumArgs(args, 2);
                message = args[1];
                Repository.gitletCommit(message);
                break;
            case "rm":
                validateNumArgs(args, 2);
                fileName = args[1];
                Repository.gitletRemoveFile(fileName);
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
                message = args[1];
                Repository.gitletFindMessage(message);
                break;
            case "status":
                validateNumArgs(args, 1);
                Repository.gitletStatus();
                break;
            case "branch":
                validateNumArgs(args, 2);
                branchName = args[1];
                Repository.gitletCreateBranch(branchName);
                break;
            case "rm-branch":
                validateNumArgs(args, 2);
                branchName = args[1];
                Repository.gitletDeleteBranch(branchName);
                break;
            case "checkout":
                if (args.length == 3 && "--".equals(args[1])) {
                    fileName = args[2];
                    Repository.gitletCheckoutFile(fileName);
                } else if (args.length == 4 && "--".equals(args[2])) {
                    commitId = args[1];
                    fileName = args[3];
                    Repository.gitletCheckoutCommitFile(commitId, fileName);
                } else if (args.length == 2) {
                    branchName = args[1];
                    Repository.gitletCheckoutBranch(branchName);
                } else {
                    throw new GitletException("Incorrect operands.");
                }
                break;
            case "reset":
                validateNumArgs(args, 2);
                commitId = args[1];
                Repository.gitletReset(commitId);
                break;
            case "merge":
                validateNumArgs(args, 2);
                branchName = args[1];
                Repository.gitletMerge(branchName);
                break;
            default:
                throw new GitletException("No command with that name exists.");
        }
    }
    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            throw new GitletException(
                    "Incorrect operands.");
        }
    }
}


