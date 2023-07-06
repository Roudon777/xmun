package xmun;
import java.util.List;

public class xmunList extends XInstance{
	Object elements[];
	int current = 0;

	xmunList(int size) {
		super(null);
		this.elements = new Object[size];
	}

	@Override
	Object get(Token name) {
		if(name.lexeme.equals("set")) {
			return new Callable() {
				@Override
				public int arity() {
					return 1;
				}

				@Override
				public Object call(Interpreter interpreter, List<Object> arguments) {
					Object elem = arguments.get(0);
					elements[current] = elem;
					current++;
					return elem;
				}
			};
		}
		else if(name.lexeme.equals("get")) {
			return new Callable() {
				@Override
				public int arity() {
					return 1;
				}
				@Override
				public Object call(Interpreter interpreter, List<Object> arguments) {
					int index = (int)(double)arguments.get(0);
					if(index >= elements.length) {
						throw new RuntimeError(name, "Index out of boundary");
					}
					return elements[index];
				}
			};
		}
		else if(name.lexeme.equals("length")) {
			return (double)elements.length;
		} 
		else if(name.lexeme.equals("remove")) {
			return new Callable() {
				@Override
				public int arity() {
					return 1;
				}
				@Override
				public Object call(Interpreter interpreter, List<Object> arguments) {
					int index = (int)(double)arguments.get(0);
					if(index >= elements.length) {
						throw new RuntimeError(name, "Index out of boundary");
					}
					elements[index] = null;
					for(int i = index; i < elements.length - 1; i++) {
						elements[i] = elements[i + 1];
					}
					elements[elements.length - 1] = null;
					current--;
					return elements;
				}
			};
		
		}else if(name.lexeme.equals("quickSort")) {
			return new Callable() {
				@Override
				public int arity() {
					return 0;
				}
				@Override
				public Object call(Interpreter interpreter, List<Object> arguments) {
					int result[]  = convertToIntArray(elements);
					quickSort(result, 0, elements.length);
					elements = convertToObjectArray(result);
					return elements;
				}
			};
		}else if(name.lexeme.equals("bubbleSort")){
			return new Callable() {
				@Override
				public int arity() {
					return 0;
				}
				@Override
				public Object call(Interpreter interpreter, List<Object> arguments) {
					int result[]  = convertToIntArray(elements);
					bubbleSort(result, 0, elements.length - 1);
					elements = convertToObjectArray(result);
					return elements;
				}
			};
		}
		else {
			throw new RuntimeException("Invalid method name: " + name.lexeme);
		}
	}

	@Override
	public String toString() {
		return "0";
	}
	
	private static void bubbleSort(int[] arr, int low, int high) {
        if (low < high) {
            for (int i = low; i < high; i++) {
                for (int j = low; j < high - i + low; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }
	
	private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }
    
    public static int[] convertToIntArray(Object[] objects) {
        int[] intArray = new int[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                intArray[i] = (int)(double) objects[i]; 
            } else {
                System.out.println("Null object at index " + i);
            }
        }
        return intArray;
    }
    
    public static Object[] convertToObjectArray(int[] intArray) {
        Object[] objectArray = new Object[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            objectArray[i] = (Object) intArray[i];  // 如果对象实际上是Integer，这将正常工作
        }
        return objectArray;
    }

}
