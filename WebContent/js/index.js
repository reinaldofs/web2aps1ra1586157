function getXmlHttp() {
var xmlhttp;
try{
 xmlhttp = new XMLHttpRequest();
}catch(ee){
 try{
  xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
 }catch(e){
  try{
   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
  }catch(E){
    xmlhttp = false;
  }
 }
}
return xmlhttp;
} 

function f_getURL(){
	return '/web2aps1ra1586157/';
}

function getUrlVars() {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,    
    function(m,key,value) {
      vars[key] = value;
    });
    return vars;
 }

function f_DeleteRows(tab){
	while(tab.rows.length>1){
		tab.deleteRow(1);
	}
}

function f_msg(icone, msg, tipo, ms){
	$.notify({
    	icon: icone, //'pe-7s-gift',
    	message: msg //""

    },{
        type: tipo,
        timer: ms,
        placement: {
            from: 'bottom',
            align: 'left'
        }
    });
}
// mostra a mensagem do servidor
function f_showMsg(msg){
	f_msg((msg.status>0) ? 'pe-7s-like2':'pe-7s-attention', msg.msg, (msg.status>0) ? 'success':'danger', 2000);
}
// obtem a lista de times
function f_getDados(query, classe, callback){
	var tmp = getXmlHttp();
	tmp.open("GET", f_getURL()+classe+"?query="+encodeURIComponent(query), true);
	tmp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	tmp.send();
	tmp.onreadystatechange = retorno;
	function retorno(){
		if(tmp.readyState == 4){
			if (tmp.status==200){	
				var ret = JSON.parse(tmp.responseText.trim());
				callback(ret);
			}else{
				callback(false);
			}
		}
	}	
}

function f_getTimes(query, callback){
	return f_getDados(query, 'TimeSearchServlet', callback);
}

function f_getBotoesCrud(metodo, id){
	return '<button class="btn btn-danger btn-fill pull-right" style="margin-left:10px" onclick="f_excluir'+metodo+'('+id+')">Excluir</button><button class="btn btn-info btn-fill pull-right" onclick="f_editar'+metodo+'('+id+')">Editar</button>';
}

function f_navigate(url, time){
	setTimeout(function(){
		document.location = url;
	}, time);
}
// recarrega os times na tela
function f_refreshTimes(query){
	query = query||''; 
	var tab = document.getElementById('tabTimes');
	f_DeleteRows(tab);
	
	f_getTimes(query,function(times){
		times.forEach(function(t){
			var r =  document.createElement("TR");
			var c1 = r.insertCell();
			var c2 = r.insertCell();
			var c3 = r.insertCell();
			var c4 = r.insertCell();
			// alimenta os dados
			c1.innerHTML = t.idTime;
			c2.innerHTML = t.nome;
			c3.innerHTML = t.sigla;
			c4.innerHTML = f_getBotoesCrud('Time', t.idTime);
			
			tab.tBodies[0].appendChild(r);
		});
	});	
}

// prepara o formulario do time quando abre a tela
function f_loadFormTime(callback){
	var parms = getUrlVars();
	// irá carregar
	if ([undefined, null, 0,''].indexOf(parms.id)<0){
		f_getTimes(parms.id,function(t){
			callback(t[0]);
		});
	}else{
		// novo cadastro
		callback({idTime:0, nome:'', sigla:''});
	}
}

// chama a página de editar time
function f_editarTime(id){
	document.location="formtime.html?id="+id;
} 

function f_incluirTime(id){
	document.location="formtime.html?new";
} 

function f_gravarTime(){
	var t={idTime:id, nome:document.getElementById('nome').value, sigla:document.getElementById('sigla').value};
	var tmp = getXmlHttp();
	tmp.open("GET", f_getURL()+"TimeChangeServlet?idtime="+encodeURIComponent(t.idTime)+"&nome="+encodeURIComponent(t.nome)+"&sigla="+encodeURIComponent(t.sigla), true);
	tmp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	tmp.send();
	tmp.onreadystatechange = retorno;
	function retorno(){
		if(tmp.readyState == 4){
			if (tmp.status==200){	
				var ret = JSON.parse(tmp.responseText.trim());
				f_showMsg(ret);   
				if (ret.status>0){
					f_navigate('times.html', 2000);
				}
			}else{
				f_showMsg({status:-1, msg:'Falha ao conectar no servidor.'});
			}
		}
	}
}

function f_excluir(id, classe, campoID, callback){
	var tmp = getXmlHttp();
	tmp.open("GET", f_getURL()+classe+"DeleteServlet?"+campoID+"="+encodeURIComponent(id));
	tmp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	tmp.send();
	tmp.onreadystatechange = retorno;
	function retorno(){
		if(tmp.readyState == 4){
			if (tmp.status==200){	
				var ret = JSON.parse(tmp.responseText.trim());
				f_showMsg(ret);   
				callback();
			}else{
				f_showMsg({status:-1, msg:'Falha ao conectar no servidor.'});
			}
		}
	}
}

function f_excluirTime(id){
	return f_excluir(id, 'Time', 'idtime', f_refreshTimes);
}

function f_buscaTime(){
	f_refreshTimes(document.getElementById('busca').value);
}
///////////////////////////////////////////////////////
//recarrega os times na tela
function f_refreshTorcedores(query){
	query = query||''; 
	var tab = document.getElementById('tabTorcedores');
	f_DeleteRows(tab);
	
	f_getPessoas(query,function(times){
		times.forEach(function(t){
			var r =  document.createElement("TR");
			var c1 = r.insertCell();
			var c2 = r.insertCell();
			var c3 = r.insertCell();
			var c4 = r.insertCell();
			var c5 = r.insertCell();
			var c6 = r.insertCell();

			// alimenta os dados
			c1.innerHTML = t.idPessoa;
			c2.innerHTML = t.nome;
			c3.innerHTML = t.email;
			c4.innerHTML = t.telefone;
			c5.innerHTML = (t.time||{nome:'Não informado'}).nome;
			c6.innerHTML = f_getBotoesCrud('Pessoa', t.idPessoa);
			
			tab.tBodies[0].appendChild(r);
		});
	});	
}

function f_getPessoas(query, callback){
	return f_getDados(query, 'PessoaSearchServlet', callback);
}

function f_editarPessoa(id){
	document.location="formtorcedor.html?id="+id;
} 

function f_incluirTorcedor(id){
	document.location="formtorcedor.html?new";
} 


function f_loadReturnTimes(callback){
	f_getTimes('', function(times){
		var select = document.getElementById('idtime');
		// cria os elementos
		times.forEach(function(e){
			var el = document.createElement('OPTION');
			el.value = e.idTime;
			el.innerHTML = e.nome;
			select.appendChild(el);
		});
		
		callback();
	});
}
function f_loadFormTorcedor(callback){
	var parms = getUrlVars();
	// irá carregar
	if ([undefined, null, 0,''].indexOf(parms.id)<0){
		f_getPessoas(parms.id,function(t){
			f_loadReturnTimes(function(){
				callback(t[0]);
			});
			
		});
	}else{
		f_loadReturnTimes(function(){
			// novo cadastro
			callback({idPessoa:0,nome:'',endereco:'',telefone:'',cpf:'',cep:'',cidade:'',idtime:0,email:'',estado:''});
		});
	}
}

function f_gravarPessoa(){
	var parms = ['nome','email','telefone','cpf','endereco','cidade','cep','idtime','estado'].map(function(field){
		console.log(field);
		return field+'='+encodeURIComponent(document.getElementById(field).value)
	}).join('&');
	
	var tmp = getXmlHttp();
	tmp.open("GET", f_getURL()+"PessoaChangeServlet?idpessoa="+encodeURIComponent(id)+'&'+parms, true);
	tmp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	tmp.send();
	tmp.onreadystatechange = retorno;
	function retorno(){
		if(tmp.readyState == 4){
			if (tmp.status==200){	
				var ret = JSON.parse(tmp.responseText.trim());
				f_showMsg(ret);   
				if (ret.status>0){
					f_navigate('torcedores.html', 2000);
				}
			}else{
				f_showMsg({status:-1, msg:'Falha ao conectar no servidor.'});
			}
		}
	}
}

function f_excluirPessoa(id){
	return f_excluir(id, 'Pessoa', 'idpessoa', f_refreshTorcedores);
}

function f_buscaPessoa(){
	f_refreshTorcedores(document.getElementById('busca').value);
}

//////////////////////////////////////////////////////////////////////////////
function f_getRelatorioTorcida(callback){
	return f_getDados('', 'RelatorioTorcidaServlet', callback);
}

function f_refreshReports(){   
        var dataPreferences = {
            series: [
                [25, 30, 20, 25]
            ]
        };
        
        var optionsPreferences = {
            donut: true,
            donutWidth: 40,
            startAngle: 0,
            total: 100,
            showLabel: true,
            axisX: {
                showGrid: true
            }
        };
    
        Chartist.Pie('#chartPreferences', dataPreferences, optionsPreferences);
        
        f_getRelatorioTorcida(function(r){
        	
        	Chartist.Pie('#chartPreferences', {
                labels: r.map(function(a){return a.torcedores+' '+a.sigla}),
                series: r.map(function(a){return a.torcedores})
              });   
        	
  
        	document.getElementById('qtdTorcedores').innerHTML = r.map(function(a){return a.torcedores}).reduce((a,b)=>a+b, 0);
        });
        
   
}