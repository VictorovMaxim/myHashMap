import myHashMap.MyHashMap;
import myHashMap.MyMap;

public class MyHashMapApplication {

	public static void main(String[] args) {
		MyMap<Integer, String> map = new MyHashMap<>();
		map.put(1, "первый");
		map.put(2, "второй");
		map.put(3, "третий");
		map.put(4, "четвертый");
		System.out.println(map.get(1));;
		System.out.println(map.get(3));
	}
}