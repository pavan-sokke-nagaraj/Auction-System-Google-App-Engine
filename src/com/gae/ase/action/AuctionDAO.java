package com.gae.ase.action;

import java.util.List;

//ekle buraya yeni gelen functionlari
public interface AuctionDAO {
	public void storeUserTemp(UserDTO user);

	public UserDTO getUserDetails(String userName);
}
