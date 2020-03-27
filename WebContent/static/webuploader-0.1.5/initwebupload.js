function initWebUploadImg(inputValId) {
	var $list = $("#thelist");   //这几个初始化全局的百度文档上没说明
	var $btn = $("#ctlBtn");	 //开始上传

	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * start
	 */

	// 优化retina, 在retina下这个值是2
	ratio = window.devicePixelRatio || 1;

	// 缩略图大小
	var thumbnailWidth = 110 * ratio;   //缩略图高度和宽度 （单位是像素），当宽高度是0~1的时候，是按照百分比计算，具体可以看api文档
	var thumbnailHeight = 110 * ratio;
	// 添加的文件数量
	var fileCount = 0;

	// 添加的文件总大小
	var fileSize = 0;
	// 添加文件限制数量
	var FILE_NUM_LIMIT = 1;
	// 单个文件大小限制(单位：B)
	var FILE_SINGLE_SIZE_LIMIT = 30000000;
	// 选择文件按钮
	var $pickBtn = $("#filePicker");
	// 选择文件-置灰按钮
	var $pickDisabledBtn = $("#filePicker_disabled");
	// 显示错误位置
	var $filePickerRrror = $("#filePicker_error");
	// 所有文件的进度信息，key为file id
	var percentages = {};
	var supportTransition = (function () {
		var s = document.createElement('p').style,
			r = 'transition' in s ||
				'WebkitTransition' in s ||
				'MozTransition' in s ||
				'msTransition' in s ||
				'OTransition' in s;
		s = null;
		return r;
	})();
	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * end
	 */
	var uploader = WebUploader.create({
		// 选完文件后，是否自动上传。
		auto: false,
		// swf文件路径
		swf: 'Uploader.swf',
		// 文件接收服务端。
		server: '/docm/a/sys/uploadFile/upload',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: '#filePicker',
		multiple: false,
		fileNumLimit: 1,
		/**
		 * hlq 2017.6.8
		 * add 删除和向右向左旋转按钮
		 * start
		 */
		fileNumLimit: FILE_NUM_LIMIT,
		// 单个文件大小限制
		fileSingleSizeLimit: FILE_SINGLE_SIZE_LIMIT,
		//赋值容器
		// 总文件大小限制
		fileSizeLimit: FILE_NUM_LIMIT * FILE_SINGLE_SIZE_LIMIT,
		// 配置生成缩略图的选项
		thumb: {
			width: 110,
			height: 110,

			// 图片质量，只有type为`image/jpeg`的时候才有效。
			quality: 70,

			// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
			allowMagnify: true,

			// 是否允许裁剪。
			crop: true,

			// 为空的话则保留原有图片格式。
			// 否则强制转换成指定的类型。
			type: 'image/jpeg'
		},

		/**
		 * hlq 2017.6.8
		 * add 删除和向右向左旋转按钮
		 * end
		 */
		// 只允许选择图片文件。
		accept: {
			  title: 'Images',
			  extensions: 'jpg,jpeg,png',
			  mimeTypes: 'image/jpg,image/jpeg,image/png'   //修改这行
			},
		method: 'POST',
	});
	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * start
	 */


	uploader.onError = function (code) {
		console.log(code);
		switch (code) {
			case 'Q_EXCEED_SIZE_LIMIT' || 'F_EXCEED_SIZE':
				var text = '上传文件大于' + FILE_SINGLE_SIZE_LIMIT / 1000 + 'kb,请修改大小'
				$filePickerRrror.text(text);
				break;

			case 'Q_TYPE_DENIED':
				$filePickerRrror.text('上传文件类型不对，请选择其他文件');
				break;
			case 'F_DUPLICATE':
				$filePickerRrror.text('上传文件重复，请选择其他文件');
				break;

		}

	};

	// 当有文件添加进来的时候
	uploader.onFileQueued = function (file) {
		$filePickerRrror.text("");
		$("#"+inputValId).val("");
		++fileCount;
		fileSize += file.size;
		addFile(file);
		togglePickBtn();

	};
	function togglePickBtn() {
		// console.log(uploader);
		if (fileCount === FILE_NUM_LIMIT) {
			$pickBtn.hide()
			$pickDisabledBtn.show()

		} else {
			$pickDisabledBtn.hide()
			$pickBtn.show()
		}

	}

	function addFile(file) {
		console.log(file);
		if ($("#showImgDiv")) {
			$("#showImgDiv").remove();
		}
		var $li = $(
			'<div id="' + file.id + '" class="file-item thumbnail" style="width:150px;position: relative;">' +
			'<p class="imgWrap"><img></p>' +
			'<div style="font-size:10px">' + file.name + '</div>' +
			'</div>'
		),
			$wrap = $li.find('p.imgWrap'),
			$btns = $('<div class="file-panel">' +
				'<span class="cancel">删除</span>' +
				'<span class="rotateRight">向右旋转</span>' +
				'<span class="rotateLeft">向左旋转</span>' +
				'</div>').appendTo($li);


		$li.on('mouseenter', function () {
			$btns.stop().animate({ height: 30 });
		});

		$li.on('mouseleave', function () {
			$btns.stop().animate({ height: 0 });
		});


		$btns.on('click', 'span', function () {
			var index = $(this).index(),
				deg;

			switch (index) {
				case 0:
					uploader.removeFile(file);
					return;

				case 1:
					file.rotation += 90;
					break;

				case 2:
					file.rotation -= 90;
					break;
			}

			if (supportTransition) {
				console.log('file.rotation', file.rotation);

				deg = 'rotate(' + file.rotation + 'deg)';
				console.log(deg);
				$wrap.css({
					'-webkit-transform': deg,
					'-mos-transform': deg,
					'-o-transform': deg,
					'transform': deg
				});
			} else {
				$wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
				// use jquery animate to rotation
				// $({
				//     rotation: rotation
				// }).animate({
				//     rotation: file.rotation
				// }, {
				//     easing: 'linear',
				//     step: function( now ) {
				//         now = now * Math.PI / 180;

				//         var cos = Math.cos( now ),
				//             sin = Math.sin( now );

				//         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
				//     }
				// });
			}


		});
		uploader.makeThumb(file, function (error, src) {   //webuploader方法
			if (error) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
			var img = $('<img src="' + src + '">');
			$wrap.empty().append(img);
		}, thumbnailWidth, thumbnailHeight);
		file.rotation = 0;
		$li.appendTo($list);
	};
	// 负责view的销毁
	function removeFile(file) {
		$("#"+inputValId).val("");
		var $li = $('#' + file.id);

		delete percentages[file.id];
		$li.off().find('.file-panel').off().end().remove();
	}

	uploader.onFileDequeued = function (file) {
		fileCount--;
		fileSize -= file.size;
		removeFile(file);
		togglePickBtn();


	};

	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * end
	 */


	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function (file, percentage) {
		var $li = $('#' + file.id),
			$percent = $li.find('.progress span');
		// 避免重复创建
		if (!$percent.length) {
			$percent = $('<p class="progress"><span></span></p>')
				.appendTo($li)
				.find('span');
		}
		$percent.css('width', percentage * 100 + '%');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function (file, response) {
		$('#' + file.id).append('<div style="color:red;font-size:10px;">上传成功</div>');
		$("#" + inputValId).val(response['_raw']);
		$("#uploadUrl").val(decodeURI(response['_raw']));
	});
	// 文件上传失败，显示上传出错。
	uploader.on('uploadError', function (file) {
		var $li = $('#' + file.id),
			$error = $li.find('div.error');
		// 避免重复创建
		if (!$error.length) {
			$error = $('<div class="error"></div>').appendTo($li);
		}
		$error.text('上传失败');
	});
	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function (file) {
		$('#' + file.id).find('.progress').remove();
	});
	$btn.on('click', function () {
		uploader.upload();
	});
}

function initWebUploadFiles(inputValId) {
	var $list = $("#thelist");   //这几个初始化全局的百度文档上没说明
	var $btn = $("#ctlBtn");	 //开始上传

	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * start
	 */

	// 优化retina, 在retina下这个值是2
	ratio = window.devicePixelRatio || 1;

	// 缩略图大小
	var thumbnailWidth = 110 * ratio;   //缩略图高度和宽度 （单位是像素），当宽高度是0~1的时候，是按照百分比计算，具体可以看api文档
	var thumbnailHeight = 110 * ratio;

	// 添加的文件数量
	var fileCount = 0;

	// 添加的文件总大小
	var fileSize = 0;
	// 添加文件限制数量
	var FILE_NUM_LIMIT = 1;
	// 单个文件大小限制(单位：B)
	var FILE_SINGLE_SIZE_LIMIT = 5000000000;
	// 选择文件按钮
	var $pickBtn = $("#filePicker");
	// 选择文件-置灰按钮
	var $pickDisabledBtn = $("#filePicker_disabled");
	// 显示错误位置
	var $filePickerRrror = $("#filePicker_error");
	// 所有文件的进度信息，key为file id
	var percentages = {};
	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * end
	 */
	var uploader = WebUploader.create({
		// 选完文件后，是否自动上传。
		auto: false,
		// swf文件路径
		swf: 'Uploader.swf',
		// 文件接收服务端。
		server: '/docm/a/sys/uploadFile/upload',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: '#filePicker',
		multiple: false,

		/**
		 * hlq 2017.6.8
		 * add 删除和向右向左旋转按钮
		 * start
		 */
		fileNumLimit: FILE_NUM_LIMIT,
		// 单个文件大小限制
		fileSingleSizeLimit: FILE_SINGLE_SIZE_LIMIT,
		// 总文件大小限制
		fileSizeLimit: FILE_NUM_LIMIT * FILE_SINGLE_SIZE_LIMIT,
		// 配置生成缩略图的选项
		thumb: {
			width: 110,
			height: 110,

			// 图片质量，只有type为`image/jpeg`的时候才有效。
			quality: 70,

			// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
			allowMagnify: true,

			// 是否允许裁剪。
			crop: true,

			// 为空的话则保留原有图片格式。
			// 否则强制转换成指定的类型。
			type: 'file'
		},
		/**
		 * hlq 2017.6.8
		 * add 删除和向右向左旋转按钮
		 * end
		 */
		// 只允许选择文件文件。
		accept: {
			title: 'files',
			extensions: 'mp4,ogg,webm',
			mimeTypes: 'video/mp4,audio/ogg,video/webm'
			//extensions: 'docx',
			//mimeTypes: ''
		},
		method: 'POST',
	});

	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * start
	 */


	uploader.onError = function (code) {
		// console.log(code);
		switch (code) {
			case 'Q_EXCEED_SIZE_LIMIT':
				var text = '上传文件大于' + FILE_SINGLE_SIZE_LIMIT / 1000 + 'kb,请修改大小'
				$filePickerRrror.text(text);
				break;

			case 'Q_TYPE_DENIED':
				$filePickerRrror.text('上传文件类型不对，请选择其他文件');
				break;
			case 'F_DUPLICATE':
				$filePickerRrror.text('上传文件重复，请选择其他文件');
				break;

		}

	};

	// 当有文件添加进来的时候
	uploader.onFileQueued = function (file) {
		$filePickerRrror.text("");
		$("#"+inputValId).val("");
		++fileCount;
		fileSize += file.size;
		addFile(file);
		togglePickBtn();

	};
	function togglePickBtn() {
		// console.log(uploader);
		if (fileCount === FILE_NUM_LIMIT) {
			$pickBtn.hide()
			$pickDisabledBtn.show()

		} else {
			$pickDisabledBtn.hide()
			$pickBtn.show()
		}

	}

	function addFile(file) {
		if ($("#showImgDiv")) {
			$("#showImgDiv").remove();
		}
		var $li = $(
			'<div id="' + file.id + '" class="file-item thumbnail" style="width:150px;position: relative;">' +
			'<img>' +
			'<div style="font-size:10px">' + file.name + '</div>' +
			'</div>'
		),
			$img = $li.find('img'),
			$btns = $('<div class="file-panel">' +
				'<span class="cancel">删除</span>' +
				// '<span class="rotateRight">向右旋转</span>' +
				// '<span class="rotateLeft">向左旋转</span>'+
				'</div>').appendTo($li);


		$li.on('mouseenter', function () {
			$btns.stop().animate({ height: 30 });
		});

		$li.on('mouseleave', function () {
			$btns.stop().animate({ height: 0 });
		});


		$btns.on('click', 'span', function () {
			var index = $(this).index(),
				deg;

			switch (index) {
				case 0:
					uploader.removeFile(file);
					return;

				case 1:
					file.rotation += 90;
					break;

				case 2:
					file.rotation -= 90;
					break;
			}

			if (supportTransition) {
				deg = 'rotate(' + file.rotation + 'deg)';
				$wrap.css({
					'-webkit-transform': deg,
					'-mos-transform': deg,
					'-o-transform': deg,
					'transform': deg
				});
			} else {
				$wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
				// use jquery animate to rotation
				// $({
				//     rotation: rotation
				// }).animate({
				//     rotation: file.rotation
				// }, {
				//     easing: 'linear',
				//     step: function( now ) {
				//         now = now * Math.PI / 180;

				//         var cos = Math.cos( now ),
				//             sin = Math.sin( now );

				//         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
				//     }
				// });
			}

		
		});
		uploader.makeThumb(file, function (error, src) {   //webuploader方法
			if (error) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
			$img.attr('src', src);
		}, thumbnailWidth, thumbnailHeight);

		$li.appendTo($list);
	};
	// 负责view的销毁
	function removeFile(file) {
		$("#"+inputValId).val("");
		var $li = $('#' + file.id);

		delete percentages[file.id];
		$li.off().find('.file-panel').off().end().remove();
	}

	uploader.onFileDequeued = function (file) {
		fileCount--;
		fileSize -= file.size;
		removeFile(file);
		togglePickBtn();


	};

	/**
	 * hlq 2017.6.8
	 * add 删除和向右向左旋转按钮
	 * end
	 */


	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function (file, percentage) {
		var $li = $('#' + file.id),
			$percent = $li.find('.progress span');
		// 避免重复创建
		if (!$percent.length) {
			$percent = $('<p class="progress"><span></span></p>')
				.appendTo($li)
				.find('span');
		}
		$percent.css('width', percentage * 100 + '%');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function (file, response) {
		$('#' + file.id).append('<div style="color:red;font-size:10px;">上传成功</div>');
		$("#" + inputValId).val(response['_raw']);
		$("#uploadUrl").val(decodeURI(response['_raw']));
	});
	// 文件上传失败，显示上传出错。
	uploader.on('uploadError', function (file) {
		var $li = $('#' + file.id),
			$error = $li.find('div.error');
		// 避免重复创建
		if (!$error.length) {
			$error = $('<div class="error"></div>').appendTo($li);
		}
		$error.text('上传失败');
	});
	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function (file) {
		$('#' + file.id).find('.progress').remove();
	});
	$btn.on('click', function () {
		uploader.upload();
	});
}