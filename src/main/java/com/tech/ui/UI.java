package com.tech.ui;

public enum UI {
	MAIN {
		@Override
		public String url() {
			return "/xml/main-view.fxml";
		}
	},
	HOME {
		@Override
		public String url() {
			return "/xml/home-view.fxml";
		}
	},
	PRODUCT {
		@Override
		public String url() {
			return "/xml/product-view.fxml";
		}
	},
	CATEGORY {
		@Override
		public String url() {
			return "/xml/category-view.fxml";
		}
	},
	BRAND {
		@Override
		public String url() {
			return "/xml/brand-view.fxml";
		}
	},
	SETTINGS {
		@Override
		public String url() {
			return "/xml/settings-view.fxml";
		}
	},
	CARD_VIEW {
		@Override
		public String url() {
			return "/xml/card-view.fxml";
		}
	};

	public abstract String url();
}
