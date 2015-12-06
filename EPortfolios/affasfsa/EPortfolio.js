
var slideShows = [];
//determine index of slide show
var index = [];
// holds interval for slide show
var myVar = [];
var url;
var studentName;
var pageTitle;
var pages;
    
var ePortfolioTitle;
var pageInfo;
function init(data){
    url = getTitle();
    pageInfo = data.page_info;
    ePortfolioTitle = data.ePortfolio_title;
    pages = data.pages;
    pageTitle = data.page_title;
    studentName = data.student_name;

    initCSS(pageInfo.layout);
    initCSS(pageInfo.color_theme);
    initCSS(pageInfo.font);
    makePages(pages);

    var banner = pageInfo.banner_image;
    makeBanner(banner);
    var content = pageInfo.content;
    var i = 0;
    while(i < content.length){
        var contDiv = document.createElement("div");
        contDiv.setAttribute('class','scontent');
        document.getElementById("ContentDiv").appendChild(contDiv);
        var divs = document.getElementById("ContentDiv");
        if(content[i].type==="Video"){
            addVideo(divs, content[i]);
        }else if (content[i].type==="Image"){
            addImage(divs, content[i]);
        }else if (content[i].type==="List"){
            addList(divs, content[i]);
        }else if(content[i].type ==="Paragraph"){
            addParagraph(divs, content[i]);
        }else if(content[i].type ==="Slide Show"){
            addSlideShow(divs, content[i], index.length);
        }else if(content[i].type === "linkParagraph"){
            addLinkParagraph(divs, content[i]);
        }
        i++;
    }
    addFooter(pageInfo.footer);
}

function addFooter(footer){
    var foot = document.createElement('h3');
    foot.innerHTML = footer;
    foot.setAttribute('class', 'footer');
    document.body.appendChild(foot);
}

function initCSS(css){
    var linkElem = document.createElement("Link");
    linkElem.setAttribute('rel', 'stylesheet');
    linkElem.setAttribute('type', 'text/css');
    linkElem.setAttribute('href', css+".css");
    
    document.head.appendChild(linkElem);
}

function getTitle(){
    var path = window.location.pathname;
    var page = path.split("/").pop();
    console.log(page + "THIS IS THE PAGE");
   return page;
}

function addVideo(div, component){
    var videlem = document.createElement("video");

   
    videlem.setAttribute('controls','');
    videlem.setAttribute('width', component.width);
    videlem.setAttribute('height', component.height);
    var sourceMP4 = document.createElement("source"); 
    sourceMP4.type = "video/mp4";
    sourceMP4.src = "Videos/" + component.file_name;
    videlem.appendChild(sourceMP4);
    videlem.setAttribute('class', 'content');
    var caps  = document.createElement('p');
    caps.innerHTML = component.caption;
    caps.setAttribute('class','caption');
    div.appendChild(caps);
    div.appendChild(videlem);
}

function makePages(pages){
    var navBar = document.getElementById('NavBar');
    var i = 0;
    while(i < pages.length){
        var a = document.createElement('a');
        a.setAttribute('href', pages[i].url);
        a.innerHTML = pages[i].page_title;
        if(a.innerHTML === pageTitle)
            a.setAttribute('class', 'currentPage');
        else
            a.setAttribute('class','link');
        navBar.appendChild(a);
        i++;
    }
}

function addImage(div, component){
    var picElem = document.createElement('img');
    picElem.setAttribute('src', "Images/" + component.image_file_name);
    picElem.setAttribute('class', component.position + 'Content');
    picElem.setAttribute('width', component.width);
    picElem.setAttribute('height', component.height);
    picElem.setAttribute('alt', 'picture');
    var caps = document.createElement('p');
    caps.innerHTML = component.caption;
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
    image.setAttribute('src', "Images/" + slideShow.slides[0].image_file_name);
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
    text.innerHTML = studentName;
    var img = null;
    if(banner!== ""){
        img = document.createElement('img');
        img.setAttribute('src', banner);
        img.setAttribute('alt','Banner Image');
        img.setAttribute('id', 'BannerImg');
    }
    ban.appendChild(text);
    if(img !== null)
        ban.appendChild(img);
}