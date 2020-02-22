package compiler;

public class ProductCommand extends Command {

  public ProductCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return args.get(0).execute()*args.get(1).execute();
  }

  @Override
  boolean isCompleteSub() {
    return args.size() == 2;
  }

  @Override
  Command createCommand(String declaration) {
    return new ProductCommand(declaration);
  }
}
