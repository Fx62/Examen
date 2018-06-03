import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MuestrasLaboratorio {

	public static void main(String[] args) {
		// Para revisar lo que se guarda en historico
		/*try {
			File file = new File("historico.dat");
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			while(true) {
				System.out.println(raf.readInt());
				System.out.println(raf.readUTF());
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} */
		
		// Este metodo carga la cola con el contenido del archivo data.dat
		cargarArchivo();
		
		// Se crea un objeto tipo Cola, el cual almacenara al objeto tipo Muestras
		Cola cola = new Cola();
		Muestras muestra = null;
		int codigo;
		String nombre;
		File file = null;
		
		/*Aqui se lee el archivo data .dat para cargar los valores en variables y asi inicializar un objeto tipo Muestras, para luego
		 * agregarlo a la cola
		 */
		try {
			file = new File("data.dat");
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			while(true) {
				codigo = raf.readInt();
				nombre = raf.readUTF();
				muestra = new Muestras(codigo, nombre);
				cola.push(muestra);
			}
		} catch(EOFException e) {
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		// Despues de que los valores fueron cargados a la cola, es borrado el archivo para que puedan guardar nuevo contenido y no dublicar la informacion
		if (file.exists()) {
			file.delete();
		}
		
		// Aqui listo la cola
		cola.list();
		
		// Aqui borro unos cuantos registros para emular un ambiente de que se han despachado 4 medicamentos
		cola.pop();
		cola.pop();
		cola.pop();
		cola.pop();
	}
	
	// Aqui se crea la cola la cola
	public static class Cola{
		private static class Node {
			private Muestras data;
			private Node next;
			private Node(Muestras data) {
				this.data = data;
			}
		}
		
		// nodos de cabeza y cola
		private Node head;
		private Node tail;
		
		// validar si la cola esta vacia
		public boolean isEmpty() {
			return head == null;
		}

		// ingresar objetos tipos Muestras a la cola
		public void push(Muestras data) {
			Node node = new Node(data);
			if (tail != null) {
				tail.next = node;
			}
			tail = node;
			if(isEmpty()) {
				head = node;
			}
		}
		
		// borrar objetos de la cola
		public void pop() {
			if(isEmpty()) {
				System.out.println("\n\nNo hay muestras en la cola");
			} else {
				System.out.println("\n\nMedicamento borrado de la cola");
				Muestras data = head.data;
				System.out.println("Codigo: " + data.getCodigo());
				System.out.println("Nombre: " + data.getNombre());
				try {
					File file = new File("historico.dat");
					RandomAccessFile raf = new RandomAccessFile(file, "rw");
					raf.seek(raf.length());
					raf.writeInt(data.getCodigo());
					raf.writeUTF(data.getNombre());
					raf.close();
					head = head.next;
					if(isEmpty()) {
						head = null;
					}
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		// listar objetos de la cola
		public void list() {
			Node temp = head;
			System.out.println("***** MUESTRAS *****");
			while(temp.next != null) {
				System.out.println("___________________________________");
				System.out.println("Codigo: " + temp.data.getCodigo());
				System.out.println("Nombre: " +  temp.data.getNombre());
				temp = temp.next;
			}
			System.out.println("___________________________________");
			System.out.println("Codigo: " + temp.data.getCodigo());
			System.out.println("Nombre: " +  temp.data.getNombre());
		}
	}
	
	// Ingreso dos arrays con codigos y medicamentos al archivo data.dat para que pueda ser leido
	public static void cargarArchivo() {
		int[] codigos = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110};
		String[] nombres = {"Abacavir", "ZIAGEN", "Abatacept", "Acarbosa", "PRECOSE", "Acebutolol", "SECTRAL", "Paracetamol", "TYLENOL", "Acetazolamida"};
		try {
			File file = new File("data.dat");
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(raf.length());
			for (byte i = 0; i < codigos.length; i++) {
				raf.writeInt(codigos[i]);
				raf.writeUTF(nombres[i]);
			}
			raf.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}

// constructor de objeto Muestras
class Muestras{
	int codigo;
	String nombre;
	public Muestras(int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}
	
	// Getters
	public int getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
