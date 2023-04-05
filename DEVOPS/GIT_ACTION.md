# NODE
```
name: Node.js CI

on:
  push:
    branches: [ main ]

jobs:
  build:

    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write

    strategy:
      matrix:
        node-version: [16.x]

    steps:
    - uses: actions/checkout@v3
    - name: set global env
      id: global_env
      run: |
        echo "::set-output name=IMAGE_NAME::crystal"
        echo "::set-output name=DOCKERHUB_IMAGE_NAME::docker.pkg.github.com/dschek/crystal/crystal"
    - name: Build and tag image
      run: |
        docker build -t "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}:latest" -t "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}:${GITHUB_SHA::8}" .
    - name: Docker login
      run: docker login docker.pkg.github.com -u $GITHUB_ACTOR -p ${{secrets.GITHUB_TOKEN}}
    - name: Publish image
      env:
        IMAGE_NAME: crystal
      run: docker push "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}"
```
# REACT
```
name: Node.js CI

on:
  push:
    branches: [ master ]

jobs:
  build:

    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write

    strategy:
      matrix:
        node-version: [16.x]

    steps:
    - name: set global env
      id: pr
      run: echo "::set-output name=NAME::restfrontreact"
    - uses: actions/checkout@v3
    - name: Use Node.js ${{ matrix.node-version }}
      uses: actions/setup-node@v3
      with:
        node-version: ${{ matrix.node-version }}
        cache: 'npm'
    - run: npm ci
    - run: npm run build 
    - name: set global env
      id: global_env
      run: echo "::set-output name=DOCKERHUB_IMAGE_NAME::docker.pkg.github.com/dschek/${{ steps.pr.outputs.NAME}}/${{ steps.pr.outputs.NAME}}"
    - name: Build and tag image
      run: docker build -t "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}:latest" -t "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}:${GITHUB_SHA::8}" .
    - name: Docker login
      run: docker login docker.pkg.github.com -u $GITHUB_ACTOR -p ${{secrets.GITHUB_TOKEN}}
    - name: Publish image
      run: docker push "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}"
```
# SPRING
```
name: Java CI with Gradle

on:
  push:
    branches: [ master ]

jobs:
  build:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write
    steps:
      - name: set global env
        id: pr
        run: echo "::set-output name=NAME::chatgpt"
      - uses: actions/checkout@v3
      - name: Set up JDK 19
        uses: actions/setup-java@v3
        with:
          java-version: '19'
          distribution: 'adopt'
          cache: gradle
      - name: Add Gradle Wrapper for strating Gradlew
        run: gradle wrapper
      - name: Build with Gradle
        run: ./gradlew build
      - name: set global env
        id: global_env
        run: echo "::set-output name=DOCKERHUB_IMAGE_NAME::docker.pkg.github.com/dschek/${{ steps.pr.outputs.NAME}}/${{ steps.pr.outputs.NAME}}"
      - name: Build and tag image
        run: docker build -t "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}:latest" -t "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}:${GITHUB_SHA::8}" .
      - name: Docker login
        run: docker login docker.pkg.github.com -u $GITHUB_ACTOR -p ${{secrets.GITHUB_TOKEN}}
      - name: Publish image
        run: docker push "${{ steps.global_env.outputs.DOCKERHUB_IMAGE_NAME }}"
```