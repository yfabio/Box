package com.tech.ui;

public enum Icons {
	SAVE {
		@Override
		public String path() {
			return "/img/24/save.png";
		}
	},EDIT {
		@Override
		public String path() {
			return "/img/24/edit.png";
		}
	},DELETE {
		@Override
		public String path() {
			return "/img/24/delete.png";
		}
	},
	CANCEL {
		@Override
		public String path() {
			return "/img/24/cancel.png";
		}
	},
	DEFAULT_IMG{
		@Override
		public String path() {
			return "/img/96/default.png";
		}
		
	}, APP {
		@Override
		public String path() {
			return "/img/48/box.png";
		}
	};
	public abstract String path();	
}
