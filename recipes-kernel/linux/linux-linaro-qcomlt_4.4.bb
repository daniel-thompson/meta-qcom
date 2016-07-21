# Copyright (C) 2014 Linaro
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Linaro Qualcomm Landing team 4.4 Kernel"

require recipes-kernel/linux/linux-linaro-qcom.inc
require recipes-kernel/linux/linux-dtb.inc
require recipes-kernel/linux/linux-qcom-bootimg.inc

LOCALVERSION ?= "-linaro-lt-qcom"
SRCBRANCH ?= "release/qcomlt-4.4"
SRCREV ?= "f404da21e128bc015dfcbb88d3daf2bcf18ef872"

COMPATIBLE_MACHINE = "(ifc6410|sd-600eval|dragonboard-410c)"

KERNEL_DEFCONFIG_aarch64 ?= "${S}/arch/arm64/configs/defconfig"
KERNEL_DEFCONFIG_apq8064 ?= "${S}/arch/arm/configs/qcom_defconfig"
KERNEL_CONFIG_FRAGMENTS += "${S}/kernel/configs/distro.config"

# append DTB, since bootloader doesn't support DTB
do_compile_append_apq8064() {
    if ! [ -e ${B}/arch/${ARCH}/boot/dts/${KERNEL_DEVICETREE} ] ; then
        oe_runmake ${KERNEL_DEVICETREE}
    fi
    cp ${KERNEL_OUTPUT} ${KERNEL_OUTPUT}.backup
    cat ${KERNEL_OUTPUT}.backup ${B}/arch/${ARCH}/boot/dts/${KERNEL_DEVICETREE} > ${KERNEL_OUTPUT}
    rm -f ${KERNEL_OUTPUT}.backup
}

# Wifi firmware has a recognizable arch :( 
ERROR_QA_remove = "arch"

QCOM_BOOTIMG_ROOTFS_dragonboard-410c = "mmcblk0p10"
QCOM_BOOTIMG_ROOTFS_ifc6410 = "mmcblk0p12"
QCOM_BOOTIMG_ROOTFS_sd-600eval = "mmcblk0p12"