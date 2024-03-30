FROM gradle:jdk17

WORKDIR /AuthTemplate

COPY ../../Desktop/dev /AuthTemplate

CMD ["gradle", "bootRun"]