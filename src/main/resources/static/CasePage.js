function callServer() {
	let plId = $("#plID").val();
	$.ajax({
		method: "GET",
		url: "/callServerJPA",
		data: { plId: plId }
	})
     .done(function(msg) {
			const jsonObj = JSON.parse(msg);
			console.log(jsonObj);
			Object.entries(jsonObj).forEach(([key, val]) => {
				$("#" + key).val(val);
			});
			$("#url").html(jsonObj.website);
	  });
}