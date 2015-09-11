// A very basic example

// Unit Testing Job
job("simple-project-unit-test-dev"){
  using 'template-django-unit-test'

  scm {
    git('https://github.com/Ooblioob/simple-project', '*/master')
  }
}

// Deployment Job
job("simple-project-deploy-dev"){
  using 'template-deploy'
  
  scm {
    git('https://github.com/Ooblioob/simple-project', '*/master')
  }

  environmentVariables(['PROJ_NAME': 'simple_project', 'INVENTORY_FILE': 'dev'])
}

// Smoke Testing Job
job("simple-project-smoke-test-dev"){
  using 'template-browser-test'

  scm {
    git('https://github.com/Ooblioob/simple-project', '*/master')
  }

  environmentVariables(['CONFIG_FILE': 'environment_dev.cfg', 'BEHAVE_TAGS': '-t=~ignore -t=smoke_testing'])
}

// Build Flow Job
buildFlowJob('simple-project-build-flow-dev'){
  using 'template-base-build-flow'

  scm {
    git('https://github.com/Ooblioob/simple-project', '*/master')
  }

  buildFlow("""
      build("simple-project-unit-test-dev")
      build("simple-project-deploy-dev")
      build("simple-project-smoke-test-dev")
    """)
}
