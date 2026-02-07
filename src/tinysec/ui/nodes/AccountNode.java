package tinysec.ui.nodes;

import tinysec.entity.Account;

public class AccountNode {
	private Account aAccount;
	private DefaultNode parentGTN;
	private String title;

	public AccountNode(DefaultNode parentGTN, Account account) {
		this.aAccount = account;
		this.title = account.getTitle();
		this.parentGTN = parentGTN;
	}

	public Account getAAccount() {
		return this.aAccount;
	}

	public DefaultNode getParentGTN() {
		return this.parentGTN;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public String toString() {
		return this.title;
	}
}
