define([
	'zepto',
	'mustache',
	'css!modules/private/msg/style.css'
],function(
	$,
	mustache
){

	var tpl = '<div class="toast-container {{type}}">\
		<div class="toast-message">{{content}}</div>\
	</div>';

	var activeClassName = 'active';
	var typeList = ['info','error','warning','success'];
	var defaultOptions = {
		type: 'info',
		content: ''
	};

	var  _createMsg = function(options){
		var opts = $.extend({}, defaultOptions, options);
		var $msg = $(mustache.render(tpl, {
			content: opts.content,
			type: opts.type
		}));
		$msg.on('webkitTransitionEnd',function(){
			if(!$msg.hasClass(activeClassName)){
				$msg.remove();
			}
		});
		$('body').append($msg);
		$msg.addClass(activeClassName);
		setTimeout(function() {
			$msg.removeClass(activeClassName);
		}, 2000);
	};


	var msg = function(content, type){
		_createMsg({
			type: (new RegExp('\\b'+type+'\\b','i')).test(typeList.join()) ? type.toLowerCase() : 'info',
			content: content
		});
	};

	msg.success = function(content){
		_createMsg({
			type: 'success',
			content: content
		});
	};
	msg.warn = function(content){
		_createMsg({
			type: 'warning',
			content: content
		});
	};
	msg.error = function(content){
		_createMsg({
			type: 'error',
			content: content
		});
	};
	msg.info = function(content){
		_createMsg({
			type: 'info',
			content: content
		});
	};

	return msg;
});