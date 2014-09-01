
public class TestClass {
	int num;
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		TestClass test = (TestClass)obj;
		return num == test.num;
	}
}
