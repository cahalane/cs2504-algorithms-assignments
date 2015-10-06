public class Test{
	public static void main(String[] args) {
		Set<Integer> set1 = new LinkedSet<>();
		set1.add(1);
		set1.add(2);
		set1.add(3);
		
		Set<Integer> set2 = new LinkedSet<>();
		set2.add(2);
		set2.add(3);
		set2.add(4);
		
		Set<Integer> set3 = new LinkedSet<>();
		set3.add(8);
		set3.add(9);

		
		System.out.println("set1: " + set1);
		System.out.println("set2: " + set2);
		System.out.println("set3: " + set3);

		System.out.println();
		System.out.println("Testing addAll. set1.addAll(set2)");
		set1.addAll(set2);
		System.out.println("set1 after: " + set1);

		System.out.println();
		System.out.println("Testing containsAll.");
		System.out.println("set1: " + set1);
		System.out.println("set2: " + set2);
		System.out.println("set1.containsAll(set2)");
		System.out.println(set1.containsAll(set2) ? "True" : "False");

		System.out.println();
		System.out.println("Testing containsAll.");
		System.out.println("set1: " + set1);
		System.out.println("set3: " + set3);
		System.out.println("set1.containsAll(set3)");
		System.out.println(set1.containsAll(set3) ? "True" : "False");


		System.out.println();
		System.out.println("Testing removeAll");
		System.out.println("set1: " + set1);
		System.out.println("set2: " + set2);
		System.out.println("set1.removeAll(set2)");
		set1.removeAll(set2);
		System.out.println("set1 after: " + set1);

		System.out.println();
		System.out.println("running set1.addAll(set3)");
		System.out.println("running set1.addAll(set2)");
		set1.addAll(set2);
		set1.addAll(set3);

		System.out.println();
		System.out.println("Testing retainAll");
		System.out.println("set1: " + set1);
		System.out.println("set2: " + set2);
		System.out.println("set1.retainAll(set2)");
		set1.retainAll(set2);
		System.out.println("set1 after: " + set1);
	}
}