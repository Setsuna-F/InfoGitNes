# GitLab Contributors

This is the application behind http://contributors.gitlab.com.

Development of this application happens at https://gitlab.com/gitlab-com/gitlab-contributors.

Historically, source code is also hosted at https://github.com/gitlabhq/gitlab-contributors.

## How to run the tests

Use the setup script to configure your application to be able to run the tests:

```
bin/setup
```

After this you can use the following rake task:

```
bundle exec rake test
```

## License

Released under the MIT License, Copyright (c) 2012–<i>ω</i> Xavier Noria.
