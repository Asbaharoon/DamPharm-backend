package run.dampharm.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SiteMapController {


	/**
	 * @return
	 * @throws IOException
	 */
	@GetMapping("25c22aa422d81bb693d1e4a92a0616bb.html")
	@ResponseBody
	public void getAdsTxt(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
//		out.println("google.com, pub-1100841741323345, DIRECT, f08c47fec0942fa0");
	}

}
