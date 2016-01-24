credit_card_dir=./CreditCard
apk_dir=$(credit_card_dir)/app/build/outputs/apk
debug_dir=./debug

all: debug_dir
	@cd $(credit_card_dir); \
	    bash gradlew assembleDebug; \
	    cd ../; \
	    cp $(apk_dir)/app-debug.apk $(debug_dir)

debug_dir:
	@mkdir debug > /dev/null 2>&1 || /bin/true


test: all
	@cd calabash ; \
	calabash-android resign ../$(debug_dir)/app-debug.apk; \
	adb install -r ../$(debug_dir)/app-debug.apk; \
	calabash-android run ../$(debug_dir)/app-debug.apk; \
	cd ../

