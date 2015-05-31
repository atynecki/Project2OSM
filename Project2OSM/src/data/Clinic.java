package data;

public class Clinic {
	private String name_;
	private String address_;
	
	public Clinic(){
		name_ = null;
		address_ = null;
	}

	public String getName_() {
		return name_;
	}

	public void setName_(String name_) {
		this.name_ = name_;
	}

	public String getAddress_() {
		return address_;
	}

	public void setAddress_(String address_) {
		this.address_ = address_;
	}



	@Override
	public String toString() {
		return "Clinic [name_=" + name_ + ", address_=" + address_
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clinic other = (Clinic) obj;
		if (address_ == null) {
			if (other.address_ != null)
				return false;
		} else if (!address_.equals(other.address_))
			return false;
		if (name_ == null) {
			if (other.name_ != null)
				return false;
		} else if (!name_.equals(other.name_))
			return false;
		return true;
	}
}
