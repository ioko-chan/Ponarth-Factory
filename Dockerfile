FROM gradle:jdk17

WORKDIR /AuthTemplate

COPY . /AuthTemplate

CMD ["gradle", "bootRun"]