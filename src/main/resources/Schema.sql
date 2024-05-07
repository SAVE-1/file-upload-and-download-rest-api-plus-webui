CREATE TABLE [dbo].[users](
	[username] [nvarchar](50) NOT NULL primary key,
	[password] [nvarchar](500) NOT NULL,
	[enabled] [bit] NOT NULL
) ON [PRIMARY]

CREATE TABLE [dbo].[authorities](
	[username] [nvarchar](50) NOT NULL primary key,
	[authority] [nvarchar](50) NOT NULL,
	FOREIGN KEY (username) references users(username)
) ON [PRIMARY]

create unique index ix_auth_username on authorities (username, authority);
