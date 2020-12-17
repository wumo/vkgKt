FROM ubuntu:18.04
RUN echo 'deb http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse' > /etc/apt/sources.list && \
    echo 'deb http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb-src http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb-src http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb-src http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb-src http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse' >> /etc/apt/sources.list && \
    echo 'deb-src http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse' >> /etc/apt/sources.list
RUN apt update
RUN apt install -y --no-install-recommends python3

RUN apt install -y --no-install-recommends software-properties-common
RUN add-apt-repository ppa:ubuntu-toolchain-r/test
RUN apt update
RUN apt install -y --no-install-recommends gcc-10 g++-10
RUN update-alternatives --install /usr/bin/cc gcc /usr/bin/gcc-10 100
RUN update-alternatives --install /usr/bin/c++ c++ /usr/bin/g++-10 100

RUN python3 -m pip install --upgrade pip
RUN pip install conan --upgrade
RUN pip install conan_package_tools --upgrade
RUN conan remote add bincrafters https://api.bintray.com/conan/bincrafters/public-conan
RUN conan remote add wumo https://api.bintray.com/conan/wumo/public

