package run.dampharm.app.model;

import lombok.Data;

@Data
public class ProductDto {
	private Long id;
	private String name;
	private String location;
	private Double price;
	private Long quantity;
	private Long categoryId;
}
