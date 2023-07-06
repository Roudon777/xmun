//> Functions callable
package xmun;

import java.util.List;

interface Callable {
//> callable-arity
  int arity();
//< callable-arity
  Object call(Interpreter interpreter, List<Object> arguments);
}
