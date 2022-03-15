
public class ArrayElement {
	public static int Max(byte[] data) {
		int MAX = 0;
		for(int i = 0; i < data.length; i++) {
			if(MAX<Math.abs(data[i])) {
				MAX=Math.abs(data[i]);
			}
		}
		return MAX;
	}
	
	public static int Sum(byte[] data) {
		int SUM = 0;
		for(int i = 0; i < data.length; i++) {
			SUM += Math.abs(data[i]);
		}
		return SUM;
	}
	
	public static int Diff(byte[] data) {
		int MAX = 0;
		for(int i = 0; i < data.length; i++) {
			if(MAX<Math.abs(data[i])) {
				MAX=Math.abs(data[i]);
			}
		}
		return MAX;
	}
	
	public static int Count(byte[] data) {
		int count = 0;
		boolean swich = true;
		for(int i = 0; i < data.length; i++) {
			if(data[i]>0) {
				if(swich == false) {
					swich = true;
					count ++;
				}
			}else if(data[i]<0) {
				if(swich == true) {
					swich = false;
				}
			}
		}
		return count;
	}
	
	public static byte[] Denoising(byte[] data) {
		for(int i = 0; i < data.length; i+=2) {
			data[i] = 0;
		}
		for(int i = 1; i < data.length; i+=2) {
			if(data[i]>=-3 && data[i] <= 3) {
				data[i] = 0;
			}
		}
		return data;
	}
	
	public static void Show(byte[] data) {
		for(int i = 1; i < data.length; i+=2) {
			if(data[i]!=0) {
				System.out.println(data[i]);
			}

		}
		System.out.println("******************************");
	}
	
	
}
