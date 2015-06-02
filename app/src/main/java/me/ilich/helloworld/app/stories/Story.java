package me.ilich.helloworld.app.stories;

import me.ilich.helloworld.app.datasource.WriteDataSource;

public interface Story {

    void loadTo(WriteDataSource dataSource);

}
