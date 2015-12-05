/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var slideShows = [];
var index = [];
var myVar = [];
function init(data){
    var pages = data.pages;
    makePages(pages);
    var banner = data.banner;
    makeBanner(banner);
    var content = data.content;//create divs
    var i = 0;
    while(i < content.length){
        var contDiv = document.createElement("div");
        contDiv.setAttribute('class','scontent');
        document.getElementById("ContentDiv").appendChild(contDiv);
        var divs = document.getElementById("ContentDiv");
        if(content[i].type==="video/mp4"){
            addVideo(divs, content[i].FilePath, content[i].type, content[i].caption);
        }else if (content[i].type==="image"){
            addImage(divs, content[i].FilePath, content[i].caption);
        }else if (content[i].type==="list"){
            addList(divs, content[i]);
        }else if(content[i].type ==="paragraph"){
            addParagraph(divs, content[i]);
        }else if(content[i].type ==="slideShow"){
            addSlideShow(divs, content[i], index.length);
        }else if(content[i].type === "linkParagraph"){
            addLinkParagraph(divs, content[i]);
        }
        i++;
    }
}

function getTitle(){
    var path = window.location.pathname;
    var page = path.split("/").pop();
    console.log(page);
   return page;
}

function addVideo(div, src, type, cap){
    var videlem = document.createElement("video");

   
    videlem.setAttribute('controls','');
    var sourceMP4 = document.createElement("source"); 
    sourceMP4.type = type;
    sourceMP4.src = src;
    videlem.appendChild(sourceMP4);
    videlem.setAttribute('class', 'content');
    var caps  = document.createElement('p');
    caps.innerHTML = cap;
    caps.setAttribute('class','caption');
    div.appendChild(caps);
    div.appendChild(videlem);
}

function makePages(pages){
    var navBar = document.getElementById('NavBar');
    var i = 0;
    while(i < pages.length){
        var a = document.createElement('a');
        a.setAttribute('href', pages[i].URL);
        a.innerHTML = pages[i].name;
        a.setAttribute('class','link');
        navBar.appendChild(a);
        i++;
    }
}

function addImage(div, file, cap){
    var picElem = document.createElement('img');
    picElem.setAttribute('src', file);
    picElem.setAttribute('class', 'content');
    picElem.setAttribute('alt', 'picture');
    var caps = document.createElement('p');
    caps.innerHTML = cap;
    caps.setAttribute('class','caption');
    div.appendChild(caps);
    div.appendChild(picElem);
}

function addList(div, list){
    var unorder = document.createElement('ul');
    var heading = document.createElement('h2');
    heading.innerHTML = list.title;
    var i = 0;
    while(i < list.elements.length){
        var elem = document.createElement('li');
        elem.innerHTML = list.elements[i];
        unorder.appendChild(elem);
        i++;
    }
    unorder.setAttribute('class','content');
    div.appendChild(heading);
    div.appendChild(unorder);
}

function addParagraph(div, paragraph){
    var header = null;
    if(paragraph.heading !== ""){
        header = document.createElement('h2');
        header.innerHTML = paragraph.heading;
    }
    var para = document.createElement('p');
    para.innerHTML = paragraph.text;
    para.setAttribute('class', 'content');
    if(header !== null){
        div.appendChild(header);
    }
    div.appendChild(para);
}

function addSlideShow(div, slideShow, i){
    slideShows.push(slideShow);
    index.push(0);
    myVar.push(null);
    var title = document.createElement('h2');
    title.innerHTML = slideShow.title;
    
    var image = document.createElement('img');
    image.setAttribute('src', slideShow.slides[0].image_path);
    image.setAttribute('alt', 'image');
    image.setAttribute('id','image_'+i);
    image.setAttribute('class', 'slide');
    
    var imageDiv = document.createElement('div');
    imageDiv.setAttribute('class', 'slideDiv');
    
    var cap = document.createElement('p');
    cap.innerHTML = slideShow.slides[0].caption;
    cap.setAttribute('id', 'caption_'+i);
    cap.setAttribute('class', 'caption');
    
    var play = document.createElement('img');
    play.setAttribute('id', 'playImage_'+i);
    play.setAttribute('type', 'button');
    play.value = 'play';
    play.setAttribute('class', 'button');
    play.setAttribute('src', 'play.jpg');
    play.setAttribute('alt', 'Play');
    
    play.onclick = function(){
        console.log(play.value);
        playPause(slideShow, i);
        console.log(play.value);
    };
   
    
    var prev = document.createElement('img');
    prev.setAttribute('id', 'prev_'+i);
    prev.setAttribute('type', 'button');
    prev.setAttribute('class', 'button');
    prev.setAttribute('src', 'previous.jpg');
    prev.setAttribute('alt', 'Previous');
    prev.onclick = function(){prevImage(slideShow, i);};
    
    
    var next = document.createElement('img');
    next.setAttribute('id', 'next_'+i);
    next.setAttribute('type', 'button');
    next.setAttribute('class', 'button');
    next.onclick= function(){nextImage(slideShow,i);};
    next.setAttribute('src', 'next.jpg');
    next.setAttribute('alt', 'Next');
    
    
    var buttonDiv = document.createElement('div');

    buttonDiv.setAttribute('class', 'buttonsDiv');
    var smallDiv = document.createElement('div');
    smallDiv.setAttribute('class', 'smallerDiv');
    smallDiv.appendChild(prev);
    smallDiv.appendChild(play);
    smallDiv.appendChild(next);
    buttonDiv.appendChild(smallDiv);
    div.appendChild(title);
    imageDiv.appendChild(image);
    div.appendChild(imageDiv);
    div.appendChild(cap);
    div.appendChild(buttonDiv);
}

function nextImage(slideShow, ind){
    index[ind]++;
    if(index[ind] === slideShow.slides.length){
        index[ind] = 0;
    }

    document.getElementById("image_"+ind).src = slideShow.slides[index[ind]].image_path;
    document.getElementById("caption_" + ind).innerHTML= slideShow.slides[index[ind]].caption;
}

function prevImage(slideShow, ind){
    index[ind]--;
    if(index[ind] === -1){
        index[ind] = slideShow.slides.length-1;
    }
    document.getElementById("image_"+ind).src = slideShow.slides[index[ind]].image_path;
    document.getElementById("caption_" + ind).innerHTML= slideShow.slides[index[ind]].caption;
}

function playPause(slideShow, i){
        var x = i;
        console.log(document.getElementById("playImage_"+i).value);
        if(document.getElementById("playImage_"+i).value==="play"){
            document.getElementById("playImage_"+i).value = "pause"; 
            document.getElementById("playImage_"+i).src="pause.jpg";
            myVar[x] = setInterval(function(){nextImage(slideShow, x);}, 3000);
        }else{
            document.getElementById("playImage_"+i).value = "play";
            document.getElementById("playImage_" + i).src="play.jpg";
            clearInterval(myVar[i]);
        }
}


function addLinkParagraph(div, text){
    var header = null;
    if(text.heading !== ""){
        header = document.createElement('h2');
        header.innerHTML = text.heading;
    }
    var para = document.createElement('p');
    var index = 0;
    var string = text.text;
    var final = "";
    var i = 0;
    while(i < string.length){
        if(string.charAt(i) === '*'){
            if(i < string.length-5 && string.charAt(i+1) ==='*' && string.charAt(i+2) ==='*'){
                var link = document.createElement('a');
                link.setAttribute('href', text.links[index]);
                link.setAttribute('class','paraLink');
                link.setAttribute('target','_blank');
                index++;
                i+=3;
                var cont = true;
                while(cont && i < string.length){
                    if(string.charAt(i)=== '*'){
                        if(string.charAt(i+1) ==='*' && string.charAt(i+2) ==='*'){
                            cont = false;
                            para.appendChild(link);
                            i+=1;
                        }else{
                            link.innerHTML+= string.charAt(i);
                        }
                        
                    }else{
                        link.innerHTML+= string.charAt(i);
                    }
                    i++;
                }
            }else{
                para.innerHTML += string.charAt(i);
            }
        }else{
            para.innerHTML += string.charAt(i);
        }
        i++;
    }
    if(header !== null)
        div.appendChild(header);
    para.setAttribute('class','content');
    div.appendChild(para);
}

function makeBanner(banner){
    var ban = document.getElementById('Banner');
    var text = document.createElement('h3');
    text.innerHTML = banner.text;
    var img = null;
    if(banner.image !== ""){
        img = document.createElement('img');
        img.setAttribute('src', banner.image);
        img.setAttribute('alt','Banner Image');
        img.setAttribute('id', 'BannerImg');
    }
    ban.appendChild(text);
    if(img !== null)
        ban.appendChild(img);
}