# 使用预装 Maven 和 JDK21 的镜像
FROM maven:3.9-amazoncorretto-21
WORKDIR /app

# 只复制必要的源代码和配置文件
COPY pom.xml .
COPY src ./src

# 安装中文字体（最小变更）
RUN yum -y install fontconfig curl \
    && mkdir -p /usr/share/fonts/noto \
    && curl -L -o /usr/share/fonts/noto/NotoSansCJKsc-Regular.otf https://github.com/googlefonts/noto-cjk/raw/main/Sans/OTF/SimplifiedChinese/NotoSansCJKsc-Regular.otf \
    && fc-cache -f \
    && yum clean all

# 使用 Maven 执行打包
RUN mvn clean package -DskipTests

# 暴露应用端口
EXPOSE 8123

# 使用生产环境配置启动应用
CMD ["java", "-jar", "/app/target/yu-ai-agent-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]