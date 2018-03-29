package luongduongquan.com.myblogapp;

/**
 * Created by luong.duong.quan on 3/29/2018.
 */

public class Blog {

	private String title, description, image;

	public Blog() {
	}

	public Blog(String title, String discription, String image) {
		this.title = title;
		this.description = discription;
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String discription) {
		this.description = discription;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
