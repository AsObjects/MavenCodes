package TestAction;

import org.springframework.stereotype.Component;

@Component("TestFirstActiong")
public class TestFirstActiong {
	
	public String execute() {
		System.out.println("OK");
		return "success";
	}
}
