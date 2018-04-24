webpackJsonp([0], [function (e, t, n) {
    (function (e, t) {
        function a() {
            var e = !1;
            return function (t) {
                (/(android|ipad|playbook|silk|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(t) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(t.substr(0, 4))) && (e = !0)
            }(navigator.userAgent || navigator.vendor || window.opera), e
        }

        function i() {
            var t = window.Tools.getQuery("img"), 
            n = path+"/crm/sjfx-group_files/1497349318710157.png";
            t && t.indexOf("!") > 0 && (t = t.split("!"), 
            n = path+"/silhouette/" + t[0] + "/" + t[1], 
          	
            e("#img_list_left_preview").attr("src", n), 
            e('[directto="template_custom"]').click()), s.loadMaskImage(n);
            var a = window.Tools.getQuery("to");
            "login" == a ? window.showLogin() : "vip" == a && window.showVIP()
        }

        function o() {
            e("#config-gridSize").jRange({
                from: 0,
                to: 25,
                step: 1,
                scale: [0, 5, 10, 15, 20, 25],
                format: "%s",
                width: 185,
                showLabels: !0,
                isRange: !1,
                ondragend: function (e) {
                    window._hmt.push(["_trackEvent", "config", "range-slider", e])
                }
            })/*, e("#canvas-container").width(p - 346 - 70)*/;
            const t = e('[directTo="left_panel_invite_list"]'), n = e("#nav-wrapper [directTo]");
            e("[directTo]").click(function () {
                const a = e(this), i = a.attr("cc"), o = a.attr("directTo");
                "scrollarea-content" == i ? (n.removeClass("active"), "left_panel_invite_list" != o && t.removeClass("active")) : a.siblings(a[0].nodeName).removeClass("active");
                const r = e("#" + o);
                a.addClass("active"), r.siblings("." + i).addClass("hide"), r.removeClass("hide"), window._hmt.push(["_trackEvent", "directTo", a.attr("directTo")])
            });
            const a = e("#config-background");
            m.isLogin() ? a.colpick({
                color: "#333", submit: 0, onChange: function (e, t) {
                    a.val("#" + t).css("border-color", "#" + t)
                }
            }) : a.click(function () {
                m.isLogin() || window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>注册登录后即可使用自定义背景颜色<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                    type: "error",
                    timeout: 3e4
                })
            });
            const i = e("#config-bold");
            e('[name="config_font_style"]').click(function () {
                "normal" == e(this).attr("value") ? (i.val("normal"), window._hmt.push(["_trackEvent", "config", "isbold", "normal"])) : (i.val("bold"), window._hmt.push(["_trackEvent", "config", "isbold", "bold"]))
            });
            const o = e("#config-dppx");
            e('[name="config_dppx"]').click(function () {
                const t = e(this).attr("value");
                if (t >= 2) {
                    if (!m.isLogin())return this.checked = !1, window._hmt.push(["_trackEvent", "config", "dppx", "2-not-allow"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>注册登录后即可使用高清文字<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    if (!m.isVip() && t >= 3)return this.checked = !1, window._hmt.push(["_trackEvent", "config", "dppx", "3,4-not-allow"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>普通会员可选择2倍高清，成为VIP会员即可选择3、4倍高清<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                        type: "error",
                        timeout: 3e4
                    })
                } else window._hmt.push(["_trackEvent", "config", "dppx", 1]);
                o.val(t)
            });
            const r = e("#config-rotate-value"), s = e("#config_rotate_step_text"), l = e("#config_rotate_single_text"), c = e('#rotate_picker_mult [name="config_rotate_mult"]');
            e('[name="config_rotate_check"]').click(function () {
                const t = e(this).attr("directTo");
                var n = "";
                if ("rotate_picker_random" == t)n = "45,90,270,315"; else if ("rotate_picker_mult" == t) {
                    var a = [];
                    c.each(function () {
                        this.checked && a.push(parseInt(this.value))
                    }), n = a.join(",")
                } else if ("rotate_picker_step" == t) {
                    var i = parseInt(s.val()) || 0;
                    if (i) {
                        for (var a = [], o = 1, d = 360 / i; o < d; o++)a.push(o * i);
                        n = a.join(",")
                    } else n = 0
                } else"rotate_picker_single" == t && (n = l.val() || "0");
                r.val(n)
            }), c.click(function () {
                if (!m.isLogin())return window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                    type: "error",
                    timeout: 3e4
                });
                if (!m.isVip() && "45" != this.value)return this.checked = !1, window._hmt.push(["_trackEvent", "config", "rotate-mult", "not-allow"]), window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>普通会员可选择45度，成为VIP会员任意选择所有<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                    type: "error",
                    timeout: 3e4
                });
                var e = [];
                c.each(function () {
                    this.checked && e.push(parseInt(this.value))
                }), r.val(e.join(","))
            }), s.change(function () {
                const t = e(this);
                if (!m.isVip())return t.val(""), m.isLogin() ? (window._hmt.push(["_trackEvent", "config", "rotate-mult", "not-allow"]), window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP会员任意选择所有<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                    type: "error",
                    timeout: 3e4
                })) : window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                    type: "error",
                    timeout: 3e4
                });
                var n = parseInt(t.val()) || 0;
                if (n < 0)n = 0, t.val("0"), r.val("0"); else if (n > 360)n = 360, t.val("360"), r.val("0"); else {
                    for (var a = [], i = 1, o = 360 / n; i < o; i++)a.push(i * n);
                    console.log("arr:", a.join(",")), r.val(a.join(","))
                }
            }), l.change(function () {
                const t = e(this);
                return m.isVip() ? void r.val(parseInt(t.val()) || 0) : (t.val(""), m.isLogin() ? (window._hmt.push(["_trackEvent", "config", "rotate-mult", "not-allow"]), window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP会员任意选择所有<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                    type: "error",
                    timeout: 3e4
                })) : window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                    type: "error",
                    timeout: 3e4
                }))
            }), e("#config-rotate-ratio").jRange({
                from: 0,
                to: 100,
                step: 5,
                scale: ["0%", "100%"],
                format: "%s",
                width: 185,
                showLabels: !0,
                isRange: !1,
                ondragend: function (e) {
                    window._hmt.push(["_trackEvent", "config", "ratate-ratio-range-slider", e])
                }
            });
            var u = null;
            const _ = e("#config-float-pick-color");
            var h = !0;
            _.colpick({
                color: "#333", submit: 0, onChange: function (e, t) {
                    return m.isLogin() ? m.isVip() ? void(u.hasClass("input-color-item") ? u.val("#" + t).css("border-color", "#" + t).css("color", "black") : "color" == u.attr("col") && (u.find(".input").val("#" + t), u.find(".text-color").css("color", "#" + t).text("#" + t), d.setColor(u.attr("idx"), "#" + t))) : void(h && (h = !1, window._hmt.push(["_trackEvent", "data-table", "select-color", "not-allow"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP会员任意选择所有<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                        type: "error",
                        timeout: 3e3
                    }), setTimeout(function () {
                        h = !0
                    }, 3e3))) : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    })
                }
            });
            var f = null;
            setTimeout(function () {
                f = e("#collorpicker_2")
            }, 1e3);
            const g = e("#floatColorPickPanel");
            e("body").click(function () {
                g.hasClass("active") && g.removeClass("active"), f && f.hasClass("active") && f.removeClass("active")
            }), e(".content-table-panel").scroll(function () {
                g.hasClass("active") && g.removeClass("active"), f && f.hasClass("active") && f.removeClass("active")
            });
            const y = e("#dataset_simple_config_word_number");
            e('[name="data_simple_config_word_number"]').click(function () {
                y.val(e(this).attr("value"))
            }), e("body").on("click", ".colpick_full_ns", function (e) {
                e.preventDefault(), e.stopPropagation()
            }), e("body").on("click", '.drop-background,.table-handle-panel [col="color"]', function (t) {
                const n = e(this);
                g.css({
                    top: n.offset().top,
                    left: n.offset().left
                }), u = n, n.hasClass("input-color-item") ? _.colpickSetColor(n.val()) : "color" == n.attr("col") && _.colpickSetColor(n.children(".text-color").text()), setTimeout(function () {
                    _.click(), f.addClass("active")
                }, 100), g.addClass("active"), t.preventDefault(), t.stopPropagation()
            }), e(".float-drop-color-pick-panel").click(function (e) {
                e.preventDefault(), e.stopPropagation()
            }), e("#left_panel_tips li .title").click(function () {
                const t = e(this).next(".content");
                t.hasClass("active") ? t.removeClass("active") : t.addClass("active")
            })
        }

        const r = n(2);
        n(3);
        const s = n(7), l = n(166), d = n(163), c = n(167), u = n(169), m = (n(170), n(162)), _ = n(171);
        n(172), n(173), n(175), n(177), n(179), n(181), n(182), n(183), n(185), window.isMobile = a(), window.Tools = {
            getQuery: function (e) {
                var t = new RegExp("(^|&)" + e + "=([^&]*)(&|$)"), n = window.location.search.substr(1).match(t);
                return null != n ? unescape(n[2]) : null
            }
        };
        const p = document.body.clientWidth, h = (document.body.clientHeight, e("#myLoading"));
        window.default_color = "#2d8cf0";
        t(function (e) {
            if (window.Noti = _, window.isMobile) {
                e("#mobileSupport").removeClass("hide");
                const t = e("#mobile_second");
               /* var n = 1, a = setInterval(function () {
                    t.html(n), n <= 1 ? (clearInterval(a), window.location.href = "") : n--
                }, 1e3)*/
            } else {
                c.init(), m.init(function (e) {
                    o(), d.init(), l.init(), h.addClass("hide"), h.css("opacity", "0.7"), s.loadEvent(), i(), u.init()
                });
                var p = r.get("show-update-log-14");
                if (window.cancelUpdateLog = function () {
                        r.set("show-update-log-14", !1, 604800), p = !1
                    }, 0 != p) {
                    const f = [];
                    f.push('<div class="vip-notifit-panel">'), f.push("\t<h2>1-18日更新日志:新增功能如下</h2>"), f.push("\t<ul>"), f.push("\t\t<li>(VIP)增加 简单编辑 里面 随机生成 数据模版的数量 的功能，包括生成数量为1和随机</li>"), f.push("\t</ul>"), f.push('\t<h2 style="color:rgba(255,255,255,0.58);">1-16日更新日志:新增功能如下</h2>'), f.push('\t<ul style="color:rgba(255,255,255,0.58);">'), f.push("\t\t<li>(VIP)增减简单数据功能，直接输入大段文字，用空格或者逗号等进行区分，一键生成模板数据</li>"), f.push("\t</ul>"), f.push('\t<h2 style="color:rgba(255,255,255,0.58);">1-11日更新日志:新增功能如下</h2>'), f.push('\t<ul style="color:rgba(255,255,255,0.58);">'), f.push("\t\t<li>增加常用的正方形，长方形模版</li>"), f.push("\t</ul>"), f.push('\t<h2 style="color:rgba(255,255,255,0.58);">12-30日更新日志:新增功能如下</h2>'), f.push('\t<ul style="color:rgba(255,255,255,0.58);">'), f.push("\t\t<li>(VIP)添加一键收藏预览功能，可批量从模板库中选择，一次性预览所有</li>"), f.push("\t\t<li>(VIP)添加模板一键搜索功能</li>"), f.push("\t</ul>"), f.push('\t<h2 style="color:rgba(255,255,255,0.58);">12-20日更新日志:新增功能如下</h2>'), f.push('\t<ul style="color:rgba(255,255,255,0.58);">'), f.push("\t\t<li>(VIP)修复部分浏览器下载4倍高清的bug</li>"), f.push("\t</ul>"), f.push('\t<h2 style="color:rgba(255,255,255,0.58);">12-19日更新日志:新增功能如下</h2>'), f.push('\t<ul style="color:rgba(255,255,255,0.58);">'), f.push("\t\t<li>(VIP)增加上传本地字体的功能</li>"), f.push("\t</ul>"), f.push('\t<h2 style="color:rgba(255,255,255,0.58);">12-17日更新日志:新增功能如下</h2>'), f.push('\t<ul style="color:rgba(255,255,255,0.58);">'), f.push("\t\t<li>(VIP)修改词频分析算法，增加脏数据筛选功能，修改字号连续性的问题</li>"), f.push("\t\t<li>(VIP)修改配色判断，增加随机着色 和 顺序着色两种</li>"), f.push("\t\t<li>(VIP)增加字号排序功能</li>"), f.push("\t</ul>"), f.push('\t<span class="ts" onclick="window.cancelUpdateLog()">1周内不再显示重复的提示</span>'), f.push("</div>"), window.Noti.notif({
                        msg: f.join(""),
                        type: "info",
                        position: "center",
                        multiline: !0,
                        autohide: !1,
                        timeout: 3e6
                    })
                }
            }
        })
    }).call(t, n(1), n(1))
}, , function (e, t) {
    var n = "yciyun/store/", a = location.hostname + "/", i = n + a, o = 604800;
    e.exports = {
        _window: window, _ttlTime: function (e) {
            return e = Math.abs(e), e = e > o ? o : e, (new Date).getTime() + 1e3 * e
        }, _get: function (e) {
            var t = (e || "").replace(i, "");
            try {
                var n = this._window.localStorage[e];
                if (n) {
                    var a = JSON.parse(n);
                    if (a && a.ttl)return a.ttl >= 0 && a.ttl <= (new Date).getTime() ? (this.remove(t), null) : a.data
                }
            } catch (o) {
                this.remove(t)
            }
            return null
        }, _set: function (e, t, n) {
            try {
                n = n || 3600;
                var a = {data: t, ttl: this._ttlTime(n)};
                this._window.localStorage[e] = JSON.stringify(a)
            } catch (i) {
                console.log(i)
            }
        }, _getKey: function (e) {
            return i + e
        }, setWindow: function (e) {
            e && (this._window = e)
        }, set: function (e, t, n) {
            this._window && (e = this._getKey(e), this._set(e, t, n))
        }, get: function (e) {
            if (this._window)return e = this._getKey(e), this._get(e)
        }, remove: function (e) {
            this._window && (e = this._getKey(e), delete this._window.localStorage[e])
        }, clear: function () {
            if (this._window)try {
                this._window.localStorage.clear()
            } catch (e) {
                this._window.localStorage = {}
            }
        }
    }
}, function (e, t, n) {
    var a = n(4);
    "string" == typeof a && (a = [[e.id, a, ""]]);
    n(6)(a, {});
    a.locals && (e.exports = a.locals)
}, function (e, t, n) {
    t = e.exports = n(5)(), t.push([e.id, "blockquote,body,button,caption,dd,div,dl,dt,fieldset,figure,form,h1,h2,h3,h4,h5,h6,hr,html,input,legend,li,menu,ol,p,pre,table,td,textarea,th,ul{margin:0;padding:0}address,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:block}table{border-collapse:collapse;border-spacing:0}caption,th{text-align:left;font-weight:400}abbr,body,fieldset,html,iframe,img{border:0}address,cite,dfn,em,i,var{font-style:normal}li{list-style:none}h1,h2,h3,h4,h5,h6,small{font-size:100%}sub,sup{font-size:83%}code,kbd,pre,samp{font-family:inherit}q:after,q:before{content:none}textarea{overflow:auto;resize:none}label,summary{cursor:default}a,button{cursor:pointer}b,em,h1,h2,h3,h4,h5,h6,strong{font-weight:700}a,a:hover,del,ins,s,u{text-decoration:none}body,button,input,keygen,legend,select,textarea{font:12px/1.14 arial;color:#333;outline:0}a,a:hover{color:#333}img{max-width:100%}body,html{width:100%;height:100%;margin:0}.hide{display:none!important}.canvas-container{display:inline-block}.clearfix:after{visibility:hidden;display:block;font-size:0;content:\" \";clear:both;height:0}.my-modal{position:absolute;display:none;z-index:999;left:0;top:0;right:0;bottom:0;background:rgba(0,0,0,.8)}.my-modal .modal-panel{position:absolute;left:50%;top:50%;padding:0;font-size:14px;line-height:24px;color:#505050;background:#fff;border-radius:5px;padding:10px}.my-modal .modal-close{color:#fff;border-radius:20px;padding:4px;font-weight:700;font-size:13px;position:absolute;right:-10px;top:-10px;height:15px;width:15px;line-height:15px;text-align:center;background:#ffab02;cursor:pointer;z-index:2;-webkit-transition:all .2s ease-in-out;transition:all .2s ease-in-out}.my-modal .modal-close:hover{-webkit-transform:rotate(180deg);transform:rotate(180deg)}.modal-show{display:block!important}.fr{float:right}.fl{float:left}* ::-webkit-scrollbar{height:0;width:0}.gradient-background{background:-webkit-gradient(linear,35% 0,100% 100%,from(#192537),to(#731a00));color:#fff}.select-no{-webkit-touch-callout:none;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none}[data-tootik]{position:relative}[data-tootik]:after{border-radius:7px;bottom:100%;box-sizing:border-box;color:#fff;content:attr(data-tootik);font-family:Century Gothic,AppleGothic,CenturyGothic,sans-serif;font-size:13px;font-style:normal;line-height:14px;max-width:320px;overflow:hidden;padding:6px 6px 5px;pointer-events:none;text-align:center;text-overflow:ellipsis;-webkit-transform:translate(-50%,12px);transform:translate(-50%,12px);-webkit-transition:opacity .3s cubic-bezier(.73,.01,0,1),-webkit-transform .3s cubic-bezier(.73,.01,0,1);transition:opacity .3s cubic-bezier(.73,.01,0,1),-webkit-transform .3s cubic-bezier(.73,.01,0,1);transition:opacity .3s cubic-bezier(.73,.01,0,1),transform .3s cubic-bezier(.73,.01,0,1);transition:opacity .3s cubic-bezier(.73,.01,0,1),transform .3s cubic-bezier(.73,.01,0,1),-webkit-transform .3s cubic-bezier(.73,.01,0,1);white-space:nowrap;z-index:100000}[data-tootik]:after,[data-tootik]:before{left:50%;opacity:0;position:absolute;-webkit-backface-visibility:hidden;backface-visibility:hidden}[data-tootik]:before{border-style:solid;border-top-width:4px;border-right-width:4px;border-bottom-width:4px;border-left-width:4px;border-bottom-width:0;content:'';height:0;top:-2px;-webkit-transform:translate(-50%,-56%);transform:translate(-50%,-56%);-webkit-transition:opacity .1s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .1s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .1s cubic-bezier(.73,.01,0,1) 0s,transform .6s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .1s cubic-bezier(.73,.01,0,1) 0s,transform .6s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) 0s;width:0;z-index:110000}[data-tootik]:after{background:#000}[data-tootik]:before{border-top-color:#000;border-right-color:transparent;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=right]:before{border-top-color:transparent;border-right-color:#000;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=bottom]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:#000;border-left-color:transparent}[data-tootik][data-tootik-conf*=left]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:transparent;border-left-color:#000}[data-tootik]:focus:after,[data-tootik]:focus:before,[data-tootik]:hover:after,[data-tootik]:hover:before{opacity:1}[data-tootik]:focus:before,[data-tootik]:hover:before{-webkit-transform:translate(-50%,-52%);transform:translate(-50%,-52%);-webkit-transition:opacity .1s cubic-bezier(.73,.01,0,1) .1s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) .1s;transition:opacity .1s cubic-bezier(.73,.01,0,1) .1s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) .1s;transition:opacity .1s cubic-bezier(.73,.01,0,1) .1s,transform .6s cubic-bezier(.73,.01,0,1) .1s;transition:opacity .1s cubic-bezier(.73,.01,0,1) .1s,transform .6s cubic-bezier(.73,.01,0,1) .1s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) .1s}[data-tootik]:focus:after,[data-tootik]:hover:after{-webkit-transform:translate(-50%,-6px);transform:translate(-50%,-6px)}[data-tootik][data-tootik-conf*=right]:before{border-style:solid;border-top-width:4px;border-right-width:4px;border-bottom-width:4px;border-left-width:4px;border-left-width:0;left:auto;right:-6px;top:50%;-webkit-transform:translate(-43%,-50%);transform:translate(-43%,-50%)}[data-tootik][data-tootik-conf*=right]:after{bottom:auto;left:100%;top:50%;-webkit-transform:translate(-12px,-50%);transform:translate(-12px,-50%)}[data-tootik][data-tootik-conf*=right]:focus:before,[data-tootik][data-tootik-conf*=right]:hover:before{-webkit-transform:translate(-47%,-50%);transform:translate(-47%,-50%)}[data-tootik][data-tootik-conf*=right]:focus:after,[data-tootik][data-tootik-conf*=right]:hover:after{-webkit-transform:translate(7px,-50%);transform:translate(7px,-50%)}[data-tootik][data-tootik-conf*=bottom]:before{border-style:solid;border-top-width:4px;border-right-width:4px;border-bottom-width:4px;border-left-width:4px;border-top-width:0;bottom:-6px;left:50%;top:auto;-webkit-transform:translate(-50%,-44%);transform:translate(-50%,-44%)}[data-tootik][data-tootik-conf*=bottom]:after{bottom:auto;left:50%;top:100%;-webkit-transform:translate(-50%,-12px);transform:translate(-50%,-12px)}[data-tootik][data-tootik-conf*=bottom]:focus:before,[data-tootik][data-tootik-conf*=bottom]:hover:before{-webkit-transform:translate(-50%,-48%);transform:translate(-50%,-48%)}[data-tootik][data-tootik-conf*=bottom]:focus:after,[data-tootik][data-tootik-conf*=bottom]:hover:after{-webkit-transform:translate(-50%,6px);transform:translate(-50%,6px)}[data-tootik][data-tootik-conf*=left]:before{border-style:solid;border-top-width:4px;border-right-width:4px;border-bottom-width:4px;border-left-width:4px;border-right-width:0;height:0;left:-2px;top:50%;-webkit-transform:translate(-58%,-50%);transform:translate(-58%,-50%);width:0}[data-tootik][data-tootik-conf*=left]:after{bottom:auto;left:auto;right:100%;top:50%;-webkit-transform:translate(12px,-50%);transform:translate(12px,-50%)}[data-tootik][data-tootik-conf*=left]:focus:after,[data-tootik][data-tootik-conf*=left]:hover:after{-webkit-transform:translate(-7px,-50%);transform:translate(-7px,-50%)}[data-tootik][data-tootik-conf*=left]:focus:before,[data-tootik][data-tootik-conf*=left]:hover:before{-webkit-transform:translate(-53%,-50%);transform:translate(-53%,-50%)}[data-tootik][data-tootik-conf*=multiline]:after{min-width:180px;text-overflow:clip;white-space:normal;word-break:break-word}[data-tootik][data-tootik-conf*=delay]:before{-webkit-transition:opacity .2s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .2s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .2s cubic-bezier(.73,.01,0,1) 0s,transform .3s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .2s cubic-bezier(.73,.01,0,1) 0s,transform .3s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) 0s}[data-tootik][data-tootik-conf*=delay]:after{-webkit-transition:opacity .3s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .3s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .3s cubic-bezier(.73,.01,0,1) 0s,transform .3s cubic-bezier(.73,.01,0,1) 0s;transition:opacity .3s cubic-bezier(.73,.01,0,1) 0s,transform .3s cubic-bezier(.73,.01,0,1) 0s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) 0s}[data-tootik][data-tootik-conf*=delay]:focus:before,[data-tootik][data-tootik-conf*=delay]:hover:before{-webkit-transition:opacity .2s cubic-bezier(.73,.01,0,1) .5s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) .5s;transition:opacity .2s cubic-bezier(.73,.01,0,1) .5s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) .5s;transition:opacity .2s cubic-bezier(.73,.01,0,1) .5s,transform .6s cubic-bezier(.73,.01,0,1) .5s;transition:opacity .2s cubic-bezier(.73,.01,0,1) .5s,transform .6s cubic-bezier(.73,.01,0,1) .5s,-webkit-transform .6s cubic-bezier(.73,.01,0,1) .5s}[data-tootik][data-tootik-conf*=delay]:focus:after,[data-tootik][data-tootik-conf*=delay]:hover:after{-webkit-transition:opacity .3s cubic-bezier(.73,.01,0,1) .4s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) .4s;transition:opacity .3s cubic-bezier(.73,.01,0,1) .4s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) .4s;transition:opacity .3s cubic-bezier(.73,.01,0,1) .4s,transform .3s cubic-bezier(.73,.01,0,1) .4s;transition:opacity .3s cubic-bezier(.73,.01,0,1) .4s,transform .3s cubic-bezier(.73,.01,0,1) .4s,-webkit-transform .3s cubic-bezier(.73,.01,0,1) .4s}[data-tootik][data-tootik-conf*=shadow]:after{box-shadow:0 2px 10px 2px rgba(0,0,0,.1)}[data-tootik][data-tootik-conf*=no-fading]:after,[data-tootik][data-tootik-conf*=no-fading]:before{-webkit-transition:none;transition:none}[data-tootik][data-tootik-conf*=no-arrow]:before{display:none}[data-tootik][data-tootik-conf*=square]:after{border-radius:0}[data-tootik][data-tootik-conf*=invert]:after{color:#000;background:#fff}[data-tootik][data-tootik-conf*=invert]:before{border-top-color:#fff;border-right-color:transparent;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=invert][data-tootik-conf*=right]:before{border-top-color:transparent;border-right-color:#fff;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=invert][data-tootik-conf*=bottom]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:#fff;border-left-color:transparent}[data-tootik][data-tootik-conf*=invert][data-tootik-conf*=left]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:transparent;border-left-color:#fff}[data-tootik][data-tootik-conf*=success]:after{background:#8bc34a}[data-tootik][data-tootik-conf*=success]:before{border-top-color:#8bc34a;border-right-color:transparent;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=success][data-tootik-conf*=right]:before{border-top-color:transparent;border-right-color:#8bc34a;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=success][data-tootik-conf*=bottom]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:#8bc34a;border-left-color:transparent}[data-tootik][data-tootik-conf*=success][data-tootik-conf*=left]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:transparent;border-left-color:#8bc34a}[data-tootik][data-tootik-conf*=info]:after{background:#29d2e4}[data-tootik][data-tootik-conf*=info]:before{border-top-color:#29d2e4;border-right-color:transparent;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=info][data-tootik-conf*=right]:before{border-top-color:transparent;border-right-color:#29d2e4;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=info][data-tootik-conf*=bottom]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:#29d2e4;border-left-color:transparent}[data-tootik][data-tootik-conf*=info][data-tootik-conf*=left]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:transparent;border-left-color:#29d2e4}[data-tootik][data-tootik-conf*=warning]:after{background:#f87d09}[data-tootik][data-tootik-conf*=warning]:before{border-top-color:#f87d09;border-right-color:transparent;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=warning][data-tootik-conf*=right]:before{border-top-color:transparent;border-right-color:#f87d09;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=warning][data-tootik-conf*=bottom]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:#f87d09;border-left-color:transparent}[data-tootik][data-tootik-conf*=warning][data-tootik-conf*=left]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:transparent;border-left-color:#f87d09}[data-tootik][data-tootik-conf*=danger]:after{background:#e91e63}[data-tootik][data-tootik-conf*=danger]:before{border-top-color:#e91e63;border-right-color:transparent;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=danger][data-tootik-conf*=right]:before{border-top-color:transparent;border-right-color:#e91e63;border-bottom-color:transparent;border-left-color:transparent}[data-tootik][data-tootik-conf*=danger][data-tootik-conf*=bottom]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:#e91e63;border-left-color:transparent}[data-tootik][data-tootik-conf*=danger][data-tootik-conf*=left]:before{border-top-color:transparent;border-right-color:transparent;border-bottom-color:transparent;border-left-color:#e91e63}[data-tootik][data-tootik='']:after,[data-tootik][data-tootik='']:before{display:none}.tipinfo{font-size:12px;zoom:1;display:inline-block;width:13px;height:13px;background:#f90;border-radius:50%;color:#fff;text-align:center;font-weight:700;line-height:13px}@-webkit-keyframes a{0%{opacity:0;bottom:-15px;max-height:0;max-width:0;margin-top:0}30%{opacity:.8;bottom:-3px}to{opacity:1;bottom:0;max-height:200px;margin-top:12px;max-width:400px}}@keyframes a{0%{opacity:0;bottom:-15px;max-height:0;max-width:0;margin-top:0}30%{opacity:.8;bottom:-3px}to{opacity:1;bottom:0;max-height:200px;margin-top:12px;max-width:400px}}@-webkit-keyframes b{0%{opacity:1;bottom:0}30%{opacity:.2;bottom:-3px}to{opacity:0;bottom:-15px}}@keyframes b{0%{opacity:1;bottom:0}30%{opacity:.2;bottom:-3px}to{opacity:0;bottom:-15px}}@-webkit-keyframes c{0%{opacity:0}30%{opacity:.5}to{opacity:.6}}@keyframes c{0%{opacity:0}30%{opacity:.5}to{opacity:.6}}@-webkit-keyframes d{0%{opacity:.6}30%{opacity:.1}to{opacity:0}}@keyframes d{0%{opacity:.6}30%{opacity:.1}to{opacity:0}}.notyf__icon--alert,.notyf__icon--confirm{height:21px;width:21px;background:#fff;border-radius:50%;display:block;margin:0 auto;position:relative}.notyf__icon--alert:after,.notyf__icon--alert:before{content:\"\";background:#ed3d3d;display:block;position:absolute;width:3px;border-radius:3px;left:9px}.notyf__icon--alert:after{height:3px;top:14px}.notyf__icon--alert:before{height:8px;top:4px}.notyf__icon--confirm:after,.notyf__icon--confirm:before{content:\"\";background:#3dc763;display:block;position:absolute;width:3px;border-radius:3px}.notyf__icon--confirm:after{height:6px;-webkit-transform:rotate(-45deg);transform:rotate(-45deg);top:9px;left:6px}.notyf__icon--confirm:before{height:11px;-webkit-transform:rotate(45deg);transform:rotate(45deg);top:5px;left:10px}.notyf__toast{display:block;overflow:hidden;-webkit-animation:a .3s forwards;animation:a .3s forwards;box-shadow:0 1px 3px 0 rgba(0,0,0,.45);position:relative;padding-right:13px}.notyf__toast.notyf--alert{background:#ed3d3d}.notyf__toast.notyf--confirm{background:#3dc763}.notyf__toast.notyf--disappear{-webkit-animation:b .3s 1 forwards;animation:b .3s 1 forwards;-webkit-animation-delay:.25s;animation-delay:.25s}.notyf__toast.notyf--disappear .notyf__message{opacity:1;-webkit-animation:b .3s 1 forwards;animation:b .3s 1 forwards;-webkit-animation-delay:.1s;animation-delay:.1s}.notyf__toast.notyf--disappear .notyf__icon{opacity:1;-webkit-animation:d .3s 1 forwards;animation:d .3s 1 forwards}.notyf__wrapper{display:table;width:100%;padding-top:20px;padding-bottom:20px;padding-right:15px;border-radius:3px}.notyf__icon{width:20%;text-align:center;font-size:1.3em;-webkit-animation:c .5s forwards;animation:c .5s forwards;-webkit-animation-delay:.25s;animation-delay:.25s}.notyf__icon,.notyf__message{display:table-cell;vertical-align:middle;opacity:0}.notyf__message{width:80%;position:relative;-webkit-animation:a .3s forwards;animation:a .3s forwards;-webkit-animation-delay:.15s;animation-delay:.15s}.notyf{position:fixed;bottom:20px;right:30px;width:20%;color:#fff;z-index:1}@media only screen and (max-width:736px){.notyf__container{width:90%;margin:0 auto;display:block;right:0;left:0}}.a-bouncein{-webkit-animation:.5s ease-out backwards;animation:.5s ease-out backwards;-webkit-animation-name:bouncein;animation-name:bouncein}.a-bounceout{-webkit-animation:1s ease-out backwards;animation:1s ease-out backwards;-webkit-animation-name:bounceout;animation-name:bounceout}@-webkit-keyframes bouncein{0%{opacity:0;-webkit-transform:scale(.3)}50%{opacity:1;-webkit-transform:scale(1.05)}70%{-webkit-transform:scale(.9)}to{-webkit-transform:scale(1)}}@keyframes bouncein{0%{opacity:0;-webkit-transform:scale(.3);transform:scale(.3)}50%{opacity:1;-webkit-transform:scale(1.05);transform:scale(1.05)}70%{-webkit-transform:scale(.9);transform:scale(.9)}to{-webkit-transform:scale(1);transform:scale(1)}}@-webkit-keyframes bounceout{0%{-webkit-transform:scale(1)}25%{-webkit-transform:scale(.95)}50%{opacity:1;-webkit-transform:scale(1.1)}to{opacity:0;-webkit-transform:scale(.3)}}@keyframes bounceout{0%{-webkit-transform:scale(1);transform:scale(1)}25%{-webkit-transform:scale(.95);transform:scale(.95)}50%{opacity:1;-webkit-transform:scale(1.1);transform:scale(1.1)}to{opacity:0;-webkit-transform:scale(.3);transform:scale(.3)}}.gelatin-btn{display:inline-block;padding:.6em 1.1em;cursor:pointer;font-size:15px;text-decoration:none;outline:none;color:#fff;background-color:#f3643b;border-radius:3px;background-clip:padding-box;box-shadow:0 0 0 -2px #cff09e,0 0 0 -1px #f3643b;border:none;-webkit-transition:-webkit-box-shadow .3s;-webkit-transition:box-shadow .3s;transition:box-shadow .3s}.gelatin-btn:focus,.gelatin-btn:hover{color:#fff;-webkit-transition-timing-function:cubic-bezier(.6,4,.3,.8);transition-timing-function:cubic-bezier(.6,4,.3,.8);-webkit-animation:gelatine .5s 1;animation:gelatine .5s 1}.gelatin-btn-secondary{background:#c8c8a9;box-shadow:0 0 0 -2px #cff09e,0 0 0 -1px #c8c8a9}.gelatin-btn-secondary:hover{box-shadow:0 0 0 2px #cff09e,0 0 0 4px #bebe99}.gelatin-btn-secondary:active,.gelatin-btn:active{background:#24cfda;-webkit-transition-duration:0;transition-duration:0;-webkit-transition-timing-function:cubic-bezier(.6,4,.3,.8);transition-timing-function:cubic-bezier(.6,4,.3,.8);-webkit-animation:gelatine .5s 1;animation:gelatine .5s 1}@keyframes gelatine{0%,to{-webkit-transform:scale(1);transform:scale(1)}25%{-webkit-transform:scale(.9,1.1);transform:scale(.9,1.1)}50%{-webkit-transform:scale(1.1,.9);transform:scale(1.1,.9)}75%{-webkit-transform:scale(.95,1.05);transform:scale(.95,1.05)}}@-webkit-keyframes gelatine{0%,to{-webkit-transform:scale(1);transform:scale(1)}25%{-webkit-transform:scale(.9,1.1);transform:scale(.9,1.1)}50%{-webkit-transform:scale(1.1,.9);transform:scale(1.1,.9)}75%{-webkit-transform:scale(.95,1.05);transform:scale(.95,1.05)}}@-webkit-keyframes shake-little{2%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}4%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}6%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}8%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}10%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}12%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}14%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}16%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}18%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}20%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}22%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}24%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}26%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}28%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}30%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}32%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}34%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}36%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}38%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}40%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}42%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}44%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}46%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}48%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}50%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}52%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}54%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}56%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}58%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}60%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}62%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}64%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}66%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}68%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}70%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}72%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}74%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}76%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}78%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}80%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}82%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}84%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}86%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}88%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}90%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}92%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}94%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}96%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}}@keyframes shake-little{2%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}4%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}6%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}8%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}10%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}12%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}14%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}16%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}18%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}20%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}22%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}24%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}26%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}28%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}30%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}32%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}34%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}36%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}38%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}40%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}42%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}44%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}46%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}48%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}50%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}52%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}54%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}56%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}58%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}60%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}62%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}64%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}66%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}68%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}70%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}72%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}74%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}76%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}78%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}80%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}82%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}84%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}86%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}88%{-webkit-transform:translate(0) rotate(.5deg);transform:translate(0) rotate(.5deg)}90%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}92%{-webkit-transform:translateY(1px) rotate(.5deg);transform:translateY(1px) rotate(.5deg)}94%{-webkit-transform:translate(1px,1px) rotate(.5deg);transform:translate(1px,1px) rotate(.5deg)}96%{-webkit-transform:translate(1px) rotate(.5deg);transform:translate(1px) rotate(.5deg)}}", ""]),
        t.locals = {
            hide: "hide",
            "canvas-container": "canvas-container",
            clearfix: "clearfix",
            "my-modal": "my-modal",
            "modal-panel": "modal-panel",
            "modal-close": "modal-close",
            "modal-show": "modal-show",
            fr: "fr",
            fl: "fl",
            "gradient-background": "gradient-background",
            "select-no": "select-no",
            tipinfo: "tipinfo",
            "notyf__icon--alert": "notyf__icon--alert",
            "notyf__icon--confirm": "notyf__icon--confirm",
            notyf__toast: "notyf__toast",
            a: "a",
            "notyf--alert": "notyf--alert",
            "notyf--confirm": "notyf--confirm",
            "notyf--disappear": "notyf--disappear",
            b: "b",
            notyf__message: "notyf__message",
            notyf__icon: "notyf__icon",
            d: "d",
            notyf__wrapper: "notyf__wrapper",
            c: "c",
            notyf: "notyf",
            notyf__container: "notyf__container",
            "a-bouncein": "a-bouncein",
            bouncein: "bouncein",
            "a-bounceout": "a-bounceout",
            bounceout: "bounceout",
            "gelatin-btn": "gelatin-btn",
            gelatine: "gelatine",
            "gelatin-btn-secondary": "gelatin-btn-secondary",
            "shake-little": "shake-little"
        }
}, function (e, t) {
    e.exports = function () {
        var e = [];
        return e.toString = function () {
            for (var e = [], t = 0; t < this.length; t++) {
                var n = this[t];
                n[2] ? e.push("@media " + n[2] + "{" + n[1] + "}") : e.push(n[1])
            }
            return e.join("")
        }, e.i = function (t, n) {
            "string" == typeof t && (t = [[null, t, ""]]);
            for (var a = {}, i = 0; i < this.length; i++) {
                var o = this[i][0];
                "number" == typeof o && (a[o] = !0)
            }
            for (i = 0; i < t.length; i++) {
                var r = t[i];
                "number" == typeof r[0] && a[r[0]] || (n && !r[2] ? r[2] = n : n && (r[2] = "(" + r[2] + ") and (" + n + ")"), e.push(r))
            }
        }, e
    }
}, function (e, t, n) {
    function a(e, t) {
        for (var n = 0; n < e.length; n++) {
            var a = e[n], i = _[a.id];
            if (i) {
                i.refs++;
                for (var o = 0; o < i.parts.length; o++)i.parts[o](a.parts[o]);
                for (; o < a.parts.length; o++)i.parts.push(d(a.parts[o], t))
            } else {
                for (var r = [], o = 0; o < a.parts.length; o++)r.push(d(a.parts[o], t));
                _[a.id] = {id: a.id, refs: 1, parts: r}
            }
        }
    }

    function i(e) {
        for (var t = [], n = {}, a = 0; a < e.length; a++) {
            var i = e[a], o = i[0], r = i[1], s = i[2], l = i[3], d = {css: r, media: s, sourceMap: l};
            n[o] ? n[o].parts.push(d) : t.push(n[o] = {id: o, parts: [d]})
        }
        return t
    }

    function o(e, t) {
        var n = f(), a = v[v.length - 1];
        if ("top" === e.insertAt)a ? a.nextSibling ? n.insertBefore(t, a.nextSibling) : n.appendChild(t) : n.insertBefore(t, n.firstChild), v.push(t); else {
            if ("bottom" !== e.insertAt)throw new Error("Invalid value for parameter 'insertAt'. Must be 'top' or 'bottom'.");
            n.appendChild(t)
        }
    }

    function r(e) {
        e.parentNode.removeChild(e);
        var t = v.indexOf(e);
        t >= 0 && v.splice(t, 1)
    }

    function s(e) {
        var t = document.createElement("style");
        return t.type = "text/css", o(e, t), t
    }

    function l(e) {
        var t = document.createElement("link");
        return t.rel = "stylesheet", o(e, t), t
    }

    function d(e, t) {
        var n, a, i;
        if (t.singleton) {
            var o = y++;
            n = g || (g = s(t)), a = c.bind(null, n, o, !1), i = c.bind(null, n, o, !0)
        } else e.sourceMap && "function" == typeof URL && "function" == typeof URL.createObjectURL && "function" == typeof URL.revokeObjectURL && "function" == typeof Blob && "function" == typeof btoa ? (n = l(t), a = m.bind(null, n), i = function () {
            r(n), n.href && URL.revokeObjectURL(n.href)
        }) : (n = s(t), a = u.bind(null, n), i = function () {
            r(n)
        });
        return a(e), function (t) {
            if (t) {
                if (t.css === e.css && t.media === e.media && t.sourceMap === e.sourceMap)return;
                a(e = t)
            } else i()
        }
    }

    function c(e, t, n, a) {
        var i = n ? "" : a.css;
        if (e.styleSheet)e.styleSheet.cssText = b(t, i); else {
            var o = document.createTextNode(i), r = e.childNodes;
            r[t] && e.removeChild(r[t]), r.length ? e.insertBefore(o, r[t]) : e.appendChild(o)
        }
    }

    function u(e, t) {
        var n = t.css, a = t.media;
        if (a && e.setAttribute("media", a), e.styleSheet)e.styleSheet.cssText = n; else {
            for (; e.firstChild;)e.removeChild(e.firstChild);
            e.appendChild(document.createTextNode(n))
        }
    }

    function m(e, t) {
        var n = t.css, a = t.sourceMap;
        a && (n += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(a)))) + " */");
        var i = new Blob([n], {type: "text/css"}), o = e.href;
        e.href = URL.createObjectURL(i), o && URL.revokeObjectURL(o)
    }

    var _ = {}, p = function (e) {
        var t;
        return function () {
            return "undefined" == typeof t && (t = e.apply(this, arguments)), t
        }
    }, h = p(function () {
        return /msie [6-9]\b/.test(window.navigator.userAgent.toLowerCase())
    }), f = p(function () {
        return document.head || document.getElementsByTagName("head")[0]
    }), g = null, y = 0, v = [];
    e.exports = function (e, t) {
        t = t || {}, "undefined" == typeof t.singleton && (t.singleton = h()), "undefined" == typeof t.insertAt && (t.insertAt = "bottom");
        var n = i(e);
        return a(n, t), function (e) {
            for (var o = [], r = 0; r < n.length; r++) {
                var s = n[r], l = _[s.id];
                l.refs--, o.push(l)
            }
            if (e) {
                var d = i(e);
                a(d, t)
            }
            for (var r = 0; r < o.length; r++) {
                var l = o[r];
                if (0 === l.refs) {
                    for (var c = 0; c < l.parts.length; c++)l.parts[c]();
                    delete _[l.id]
                }
            }
        }
    };
    var b = function () {
        var e = [];
        return function (t, n) {
            return e[t] = n, e.filter(Boolean).join("\n")
        }
    }()
}, function (e, t, n) {
    (function (t) {
        function a(e) {
            c = document.createElement("canvas"), c.width = e.width, c.height = e.height;
            var t = c.getContext("2d");
            t.drawImage(e, 0, 0, e.width, e.height);
            for (var n = t.getImageData(0, 0, c.width, c.height), a = t.createImageData(n), i = 0; i < n.data.length; i += 4) {
                var o = n.data[i] + n.data[i + 1] + n.data[i + 2], r = n.data[i + 3];
                r < 128 || o > 384 ? (a.data[i] = a.data[i + 1] = a.data[i + 2] = 255, a.data[i + 3] = 0) : (a.data[i] = a.data[i + 1] = a.data[i + 2] = 0, a.data[i + 3] = 255)
            }
            t.putImageData(a, 0, 0)
        }

        function i() {
            p.addClass("loading");
            var e = parseFloat(f.val()), t = x || _.width(), n = Y || _.height(), a = t, i = n;
            1 !== e ? (u.css({width: t + "px", height: n + "px"}), a *= e, i *= e) : u.css({
                width: "",
                height: ""
            }), u.attr("width", a), u.attr("height", i), m.css({width: a + "px", height: i + "px"});
            const s = y.val();
            _.css("background-color", s);
            var d = {
                gridSize: g.val(),
                weightFactor: function (e) {
                    return Math.pow(e, 2.3) * u.width() / 1024
                },
                fontFamily: D,
                shape: "circle",
                fontWeight: v.val(),
                rotateRatio: parseFloat(w.val() / 100) || 0,
                rotationRange: (M.val() || "0").split(","),
                backgroundColor: L ? "rgba(255,255,255,0.01)" : s
            };
            if (r.set("canvas_options", d), 1 !== e)if ("gridSize" in d || (d.gridSize = 8), d.gridSize *= e, d.origin && ("number" == typeof d.origin[0] && (d.origin[0] *= e), "number" == typeof d.origin[1] && (d.origin[1] *= e)), "weightFactor" in d || (d.weightFactor = 1), "function" == typeof d.weightFactor) {
                var h = d.weightFactor;
                d.weightFactor = function () {
                    return h.apply(this, arguments) * e
                }
            } else d.weightFactor *= e;
            const b = window.default_color, k = l.getData();
            d.list = k.data;
            const T = k.color;
            d.minRotation = 0, d.minSize = 4, d.maxRotation = 360;
            const S = c.getContext("2d");
            if (d.color = function (e, t, n, a, i, o, r) {
                    S.getImageData(o, r, 1, 1).data;
                    return T[e] || b
                }, c) {
                d.clearCanvas = !1;
                var z = document.createElement("canvas").getContext("2d");
                z.fillStyle = d.backgroundColor, z.fillRect(0, 0, 1, 1);
                var H = z.getImageData(0, 0, 1, 1).data, j = document.createElement("canvas");
                j.width = u[0].width, j.height = u[0].height;
                var F = j.getContext("2d");
                F.drawImage(c, 0, 0, c.width, c.height, 0, 0, j.width, j.height);
                for (var P = F.getImageData(0, 0, canvas.width, canvas.height), C = F.createImageData(P), E = 0; E < P.data.length; E += 4)P.data[E + 3] > 128 ? (C.data[E] = H[0], C.data[E + 1] = H[1], C.data[E + 2] = H[2], C.data[E + 3] = H[3]) : (C.data[E] = H[0], C.data[E + 1] = H[1], C.data[E + 2] = H[2], C.data[E + 3] = H[3] ? H[3] - 1 : 0);
                F.putImageData(C, 0, 0), F = u[0].getContext("2d"), F.drawImage(j, 0, 0), j = F = P = C = z = H = void 0
            }
            L ? _.addClass("transparent") : _.removeClass("transparent"), d.clearCanvas || (m.empty(), m.css("background-color", d.backgroundColor)), o([u[0], m[0]], d)
        }

        const o = n(8), r = (n(9), n(2)), s = n(162), l = n(163), d = n(165);
        var c, u = t("#canvas"), m = t("#html-canvas"), _ = t("#canvas-container"), p = t("#btn-reload"), h = t("#config-mask"), f = t("#config-dppx"), g = (t("#link-webfont"), t("#config-gridSize")), y = t("#config-background"), v = t("#config-bold"), b = t("#fontfamilySelect"), w = t("#config-rotate-ratio"), M = t("#config-rotate-value"), k = !0, L = !1;
        h.on("change", function () {
            c = null;
            var e = h[0].files[0];
            if (e) {
                var t = window.URL.createObjectURL(e), n = new Image;
                n.src = t, n.onload = function () {
                    window.URL.revokeObjectURL(t), a(n)
                }
            }
        });
        _.width(), _.height();
        var x = 0, Y = 0, D = "comic sans ms";
        const T = (document.getElementById("temp_image"), [{
            family: "comic sans ms",
            name: "Comic Sans MS"
        }, {family: "Times, serif", name: "Times, serif"}, {family: "hoefler text", name: "Hoefler Text"}, {
            vip: !0,
            down: !1,
            family: "奇思源黑",
            name: "奇思源黑",
            "class": "font-qsgyrt",
            img: "字体管家奇思源黑.png",
            file: "奇思源黑.woff2"
        }, {vip: !0, family: "helvetica", name: "Helvetica"}, {
            vip: !0,
            family: "myriad pro",
            name: "Myriad Pro"
        }, {vip: !0, family: "delicious", name: "Delicious"}, {
            vip: !0,
            down: !1,
            family: "奇思悠然体",
            name: "奇思悠然体",
            "class": "font-qsgyrt",
            img: "字体管家奇思悠然体.png",
            file: "奇思悠然体.woff2"
        }, {
            vip: !0,
            down: !1,
            family: "奇思粗萌体",
            name: "奇思粗萌体",
            "class": "font-qsgyrt",
            img: "字体管家奇思粗萌体.png",
            file: "奇思粗萌体.woff2"
        }, {vip: !0, family: "verdana", name: "Verdana"}, {vip: !0, family: "georgia", name: "Georgia"}, {
            vip: !0,
            down: !1,
            family: "字体管家极细黑",
            name: "字体管家极细黑",
            "class": "font-qsgyrt",
            img: "字体管家极细黑.png",
            file: "字体管家极细黑.woff2"
        }, {vip: !0, family: "impact", name: "Impact"}, {vip: !0, family: "monaco", name: "Monaco"}, {
            vip: !0,
            family: "optima",
            name: "Optima"
        }, {
            vip: !0,
            down: !1,
            family: "字体管家润行",
            name: "字体管家润行",
            "class": "font-qsgyrt",
            img: "字体管家润行.png",
            file: "字体管家润行.woff2"
        }, {
            vip: !0,
            down: !1,
            family: "字体管家版宋",
            name: "字体管家版宋",
            "class": "font-qsgyrt",
            img: "字体管家版宋.png",
            file: "字体管家版宋.woff",
            file2: "字体管家版宋.woff2"
        }, {vip: !0, family: "plaster", name: "Plaster"}, {vip: !0, family: "engagement", name: "Engagement"}, {
            vip: !0,
            down: !1,
            family: "奇思古粗体",
            name: "奇思古粗体",
            "class": "font-qsgct",
            img: "字体管家奇思古粗体.png",
            file: "奇思古粗体.woff2"
        }, {
            vip: !0,
            down: !1,
            family: "字体管家胖小儿",
            name: "字体管家胖小儿",
            "class": "font-qsgyrt",
            img: "字体管家胖小儿.png",
            file: "字体管家胖小儿.woff",
            file2: "字体管家胖小儿.woff2"
        }, {vip: !0, family: "courier", name: "Courier"}]), S = t(".family-panel"), z = function () {
            const e = [];
            for (var t = 0, n = T.length; t < n; t++)e.push('<option value="">' + T[t].name + "</option>");
            b.html(e.join("")), b.selectric({
                optionsItemBuilder: function (e, t, n) {
                    const a = T[e.index];
                    return a.img ? '<div class="font-img ' + (a.vip ? "font-vip" : "") + ' "><img src=""/></div>' : '<div class="font-text ' + (a.vip ? "font-vip" : "") + '">' + a.name + "</div>"
                }, onChange: function (e, t, n) {
                    S.removeClass("font-loading");
                    const a = S.find(".selectric-items .selectric-scroll .selected").attr("data-index");
                    if (a) {
                        const o = T[a];
                        if (!s.isLogin())return window.Noti.notif({
                            msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                            type: "error",
                            timeout: 3e4
                        });
                        if (o.vip && !s.isVip())return window._hmt.push(["_trackEvent", "config", "font-family", "not-allow"]), window.Noti.notif({
                            msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP会员后即可使用高清中文字体<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                            type: "error",
                            timeout: 3e4
                        });
                        if (D = o.family, !o.down && o.file) {
                            const r = window.Noti.notif({
                                msg: "<div style='line-height:22px;margin-top:10px;'>字体有点大，正在下载中..</div>",
                                type: "success",
                                timeout: 1e6
                            });
                            window._hmt.push(["_trackEvent", "config", "font-family", D]), S.addClass("font-loading");
                            const l = "//oss.ymark.cc/yeelogo/font/" + o.file;
                            S.append('<style type="text/css">@font-face {font-family: "' + o.family + '"; src: url(' + l + ') format("truetype");}.' + o["class"] + '{font-family:"' + o.family + '"; }</style>'), d.load({
                                custom: {families: [o.family]},
                                loading: function () {
                                },
                                active: function () {
                                    T[a].down = !0, S.removeClass("font-loading"), i(), setTimeout(function () {
                                        r.dismiss()
                                    }, 1e3)
                                },
                                inactive: function () {
                                },
                                fontloading: function (e, t) {
                                },
                                fontactive: function (e, t) {
                                },
                                fontinactive: function (e, t) {
                                }
                            })
                        } else i()
                    }
                }
            })
        }, H = t("#localfont-preview");
        window.onFontUpload = function () {
            if (!s.isLogin())return window.Noti.notif({
                msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                type: "error",
                timeout: 3e4
            });
            if (!s.isVip())return window._hmt.push(["_trackEvent", "config", "font-family-local", "not-allow"]), window.Noti.notif({
                msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP，解锁全部功能<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                type: "error",
                timeout: 3e4
            });
            window._hmt.push(["_trackEvent", "config", "font-family-local", "upload"]);
            const e = document.getElementById("font_upload_file");
            if (!(e.files.length <= 0)) {
                const t = e.files[0], n = t.name, a = n.substr(n.lastIndexOf(".") + 1);
                if (",ttf,otf,woff2,woff,".indexOf(a) < 0)return window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>字体格式不正确，支持(ttf,otf,woff2,woff)</div>",
                    type: "error",
                    timeout: 1e4
                });
                const o = "custon-family-" + (new Date).getTime();
                S.append('<style type="text/css">@font-face {font-family: "' + o + '"; src: url(' + window.URL.createObjectURL(t) + ') format("truetype");}.' + o + '{font-family:"' + o + '"; }</style>'), D = o;
                const r = window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>加载中..</div>",
                    type: "success",
                    timeout: 1e6
                });
                H.attr("class", ""), H.addClass(o), setTimeout(function () {
                    i(), r.dismiss()
                }, 1e3)
            }
        }, e.exports = {
            loadEvent: function () {
                return o.isSupported ? ("devicePixelRatio" in window && 1 !== window.devicePixelRatio, u.on("wordcloudstop", function (e) {
                    p.removeClass("loading")
                }), p.on("click", function () {
                    i()
                }), t("#bck-check-transparent").change(function () {
                    return s.isLogin() ? s.isVip() ? void(L = this.checked) : (this.checked = !1, window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP会员后即可使用透明背景<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>点我查看</span></div>",
                        type: "error",
                        timeout: 1e4
                    })) : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    })
                }), void z()) : void(k = !1)
            }, loadMaskImage: function (e) {
                var t = new Image;
                t.onload = function () {
                    const e = t.width, n = t.height, o = _.width(), r = _.height();
                    var s = 0, l = 0;
                    o > e && r > n ? o > r ? s = r * e / n : l = o * n / e : (s = Math.min(o, e), l = Math.min(r, n), s < l ? l = s * n / e : s = l * e / n), x = s, Y = l, a(t), i()
                }, t.crossOrigin = "Anonymous", t.src = e
            }
        }
    }).call(t, n(1))
}, function (e, t, n) {
    var a, i;
    window.setImmediate || (window.setImmediate = function () {
        return window.msSetImmediate || window.webkitSetImmediate || window.mozSetImmediate || window.oSetImmediate || function () {
                if (!window.postMessage || !window.addEventListener)return null;
                var e = [void 0], t = "zero-timeout-message", n = function (n) {
                    var a = e.length;
                    return e.push(n), window.postMessage(t + a.toString(36), "*"), a
                };
                return window.addEventListener("message", function (n) {
                    if ("string" == typeof n.data && n.data.substr(0, t.length) === t) {
                        n.stopImmediatePropagation();
                        var a = parseInt(n.data.substr(t.length), 36);
                        e[a] && (e[a](), e[a] = void 0)
                    }
                }, !0), window.clearImmediate = function (t) {
                    e[t] && (e[t] = void 0)
                }, n
            }() || function (e) {
                window.setTimeout(e, 0)
            }
    }()), window.clearImmediate || (window.clearImmediate = function () {
        return window.msClearImmediate || window.webkitClearImmediate || window.mozClearImmediate || window.oClearImmediate || function (e) {
                window.clearTimeout(e)
            }
    }()), function (n) {
        var o = function () {
            var e = document.createElement("canvas");
            if (!e || !e.getContext)return !1;
            var t = e.getContext("2d");
            return !!t && (!!t.getImageData && (!!t.fillText && (!!Array.prototype.some && !!Array.prototype.push)))
        }(), r = function () {
            if (o) {
                for (var e, t, n = document.createElement("canvas").getContext("2d"), a = 20; a;) {
                    if (n.font = a.toString(10) + "px sans-serif", n.measureText("Ｗ").width === e && n.measureText("m").width === t)return a + 1;
                    e = n.measureText("Ｗ").width, t = n.measureText("m").width, a--
                }
                return 0
            }
        }(), s = function (e) {
            for (var t, n, a = e.length; a; t = Math.floor(Math.random() * a), n = e[--a], e[a] = e[t], e[t] = n);
            return e
        }, l = function (e, t) {
            return Math.floor(e + Math.random() * (t - e))
        }, d = function (e, t) {
            function n(e, t) {
                return "hsl(" + (360 * Math.random()).toFixed() + "," + (30 * Math.random() + 70).toFixed() + "%," + (Math.random() * (t - e) + e).toFixed() + "%)"
            }

            if (o) {
                Array.isArray(e) || (e = [e]), e.forEach(function (t, n) {
                    if ("string" == typeof t) {
                        if (e[n] = document.getElementById(t), !e[n])throw"The element id specified is not found."
                    } else if (!t.tagName && !t.appendChild)throw"You must pass valid HTML elements, or ID of the element."
                });
                var a = {
                    list: [],
                    fontFamily: '"Trebuchet MS", "Heiti TC", "微軟正黑體", "Arial Unicode MS", "Droid Fallback Sans", sans-serif',
                    fontWeight: "normal",
                    color: "random-dark",
                    minSize: 0,
                    weightFactor: 1,
                    clearCanvas: !0,
                    backgroundColor: "#fff",
                    gridSize: 8,
                    drawOutOfBound: !1,
                    origin: null,
                    drawMask: !1,
                    maskColor: "rgba(255,0,0,0.3)",
                    maskGapWidth: .3,
                    wait: 0,
                    abortThreshold: 0,
                    abort: function () {
                    },
                    minRotation: -Math.PI / 2,
                    maxRotation: Math.PI / 2,
                    rotationSteps: 0,
                    rotationRange: [0, 45, 90, 135, 180, 225, 270, 315, 360],
                    rotateRatio: .1,
                    shuffle: !0,
                    shape: "circle",
                    ellipticity: .65,
                    classes: null,
                    hover: null,
                    click: null
                };
                if (t)for (var i in t)i in a && (a[i] = t[i]);
                if ("function" != typeof a.weightFactor) {
                    var d = a.weightFactor;
                    a.weightFactor = function (e) {
                        return e * d
                    }
                }
                if ("function" != typeof a.shape)switch (a.shape) {
                    case"circle":
                    default:
                        a.shape = "circle";
                        break;
                    case"cardioid":
                        a.shape = function (e) {
                            return 1 - Math.sin(e)
                        };
                        break;
                    case"diamond":
                        a.shape = function (e) {
                            var t = e % (2 * Math.PI / 4);
                            return 1 / (Math.cos(t) + Math.sin(t))
                        };
                        break;
                    case"square":
                        a.shape = function (e) {
                            return Math.min(1 / Math.abs(Math.cos(e)), 1 / Math.abs(Math.sin(e)))
                        };
                        break;
                    case"triangle-forward":
                        a.shape = function (e) {
                            var t = e % (2 * Math.PI / 3);
                            return 1 / (Math.cos(t) + Math.sqrt(3) * Math.sin(t))
                        };
                        break;
                    case"triangle":
                    case"triangle-upright":
                        a.shape = function (e) {
                            var t = (e + 3 * Math.PI / 2) % (2 * Math.PI / 3);
                            return 1 / (Math.cos(t) + Math.sqrt(3) * Math.sin(t))
                        };
                        break;
                    case"pentagon":
                        a.shape = function (e) {
                            var t = (e + .955) % (2 * Math.PI / 5);
                            return 1 / (Math.cos(t) + .726543 * Math.sin(t))
                        };
                        break;
                    case"star":
                        a.shape = function (e) {
                            var t = (e + .955) % (2 * Math.PI / 10);
                            return (e + .955) % (2 * Math.PI / 5) - 2 * Math.PI / 10 >= 0 ? 1 / (Math.cos(2 * Math.PI / 10 - t) + 3.07768 * Math.sin(2 * Math.PI / 10 - t)) : 1 / (Math.cos(t) + 3.07768 * Math.sin(t))
                        }
                }
                a.gridSize = Math.max(Math.floor(a.gridSize), 4);
                var c, u, m, _, p, h, f, g = a.gridSize, y = g - a.maskGapWidth, v = (Math.abs(Math.floor(a.rotationSteps)), Math.min(a.maxRotation, a.minRotation), a.rotationRange || []), b = a.rotationRange.length;
                switch (a.color) {
                    case"random-dark":
                        f = function () {
                            return n(10, 50)
                        };
                        break;
                    case"random-light":
                        f = function () {
                            return n(50, 90)
                        };
                        break;
                    default:
                        "function" == typeof a.color && (f = a.color)
                }
                var w = null;
                "function" == typeof a.classes && (w = a.classes);
                var M, k = !1, L = [], x = function (e) {
                    var t, n, a = e.currentTarget, i = a.getBoundingClientRect();
                    e.touches ? (t = e.touches[0].clientX, n = e.touches[0].clientY) : (t = e.clientX, n = e.clientY);
                    var o = t - i.left, r = n - i.top, s = Math.floor(o * (a.width / i.width || 1) / g), l = Math.floor(r * (a.height / i.height || 1) / g);
                    return L[s][l]
                }, Y = function (e) {
                    var t = x(e);
                    if (M !== t)return M = t, t ? void a.hover(t.item, t.dimension, e) : void a.hover(void 0, void 0, e)
                }, D = function (e) {
                    var t = x(e);
                    t && (a.click(t.item, t.dimension, e), e.preventDefault())
                }, T = [], S = function (e) {
                    if (T[e])return T[e];
                    var t = 8 * e, n = t, i = [];
                    for (0 === e && i.push([_[0], _[1], 0]); n--;) {
                        var o = 1;
                        "circle" !== a.shape && (o = a.shape(n / t * 2 * Math.PI)), i.push([_[0] + e * o * Math.cos(-n / t * 2 * Math.PI), _[1] + e * o * Math.sin(-n / t * 2 * Math.PI) * a.ellipticity, n / t * 2 * Math.PI])
                    }
                    return T[e] = i, i
                }, z = function () {
                    return a.abortThreshold > 0 && (new Date).getTime() - h > a.abortThreshold
                }, H = function () {
                    return 0 === a.rotateRatio || Math.random() > a.rotateRatio || 0 === b ? 0 : v[l(0, b)] * Math.PI / 180
                }, j = function (e, t, n) {
                    var i = !1, o = a.weightFactor(t);
                    if (o <= a.minSize)return !1;
                    var s = 1;
                    o < r && (s = function () {
                        for (var e = 2; e * o < r;)e += 2;
                        return e
                    }());
                    var l = document.createElement("canvas"), d = l.getContext("2d", {willReadFrequently: !0});
                    d.font = a.fontWeight + " " + (o * s).toString(10) + "px " + a.fontFamily;
                    var c = d.measureText(e).width / s, u = Math.max(o * s, d.measureText("m").width, d.measureText("Ｗ").width) / s, m = c + 2 * u, _ = 3 * u, p = Math.ceil(m / g), h = Math.ceil(_ / g);
                    m = p * g, _ = h * g;
                    var f = -c / 2, y = .4 * -u, v = Math.ceil((m * Math.abs(Math.sin(n)) + _ * Math.abs(Math.cos(n))) / g), b = Math.ceil((m * Math.abs(Math.cos(n)) + _ * Math.abs(Math.sin(n))) / g), w = b * g, M = v * g;
                    l.setAttribute("width", w), l.setAttribute("height", M), i && (document.body.appendChild(l), d.save()), d.scale(1 / s, 1 / s), d.translate(w * s / 2, M * s / 2), d.rotate(-n), d.font = a.fontWeight + " " + (o * s).toString(10) + "px " + a.fontFamily, d.fillStyle = "#000", d.textBaseline = "middle", d.fillText(e, f * s, (y + .5 * o) * s);
                    var k = d.getImageData(0, 0, w, M).data;
                    if (z())return !1;
                    i && (d.strokeRect(f * s, y, c * s, u * s), d.restore());
                    for (var L, x, Y, D = [], T = b, S = [v / 2, b / 2, v / 2, b / 2]; T--;)for (L = v; L--;) {
                        Y = g;
                        e:{
                            for (; Y--;)for (x = g; x--;)if (k[4 * ((L * g + Y) * w + (T * g + x)) + 3]) {
                                D.push([T, L]), T < S[3] && (S[3] = T), T > S[1] && (S[1] = T), L < S[0] && (S[0] = L), L > S[2] && (S[2] = L), i && (d.fillStyle = "rgba(255, 0, 0, 0.5)", d.fillRect(T * g, L * g, g - .5, g - .5));
                                break e
                            }
                            i && (d.fillStyle = "rgba(0, 0, 255, 0.5)", d.fillRect(T * g, L * g, g - .5, g - .5))
                        }
                    }
                    return i && (d.fillStyle = "rgba(0, 255, 0, 0.5)", d.fillRect(S[3] * g, S[0] * g, (S[1] - S[3] + 1) * g, (S[2] - S[0] + 1) * g)), {
                        mu: s,
                        occupied: D,
                        bounds: S,
                        gw: b,
                        gh: v,
                        fillTextOffsetX: f,
                        fillTextOffsetY: y,
                        fillTextWidth: c,
                        fillTextHeight: u,
                        fontSize: o
                    }
                }, F = function (e, t, n, i, o) {
                    for (var r = o.length; r--;) {
                        var s = e + o[r][0], l = t + o[r][1];
                        if (s >= u || l >= m || s < 0 || l < 0) {
                            if (!a.drawOutOfBound)return !1
                        } else if (!c[s][l])return !1
                    }
                    return !0
                }, P = function (t, n, i, o, r, s, l, d, c) {
                    var u, m = i.fontSize;
                    u = f ? f(o, r, m, s, l, t, n) : a.color;
                    var _;
                    _ = w ? w(o, r, m, s, l) : a.classes;
                    var p, h = i.bounds;
                    p = {
                        x: (t + h[3]) * g,
                        y: (n + h[0]) * g,
                        w: (h[1] - h[3] + 1) * g,
                        h: (h[2] - h[0] + 1) * g
                    }, e.forEach(function (e) {
                        if (e.getContext) {
                            var r = e.getContext("2d"), s = i.mu;
                            r.save(), r.scale(1 / s, 1 / s), r.font = a.fontWeight + " " + (m * s).toString(10) + "px " + a.fontFamily, r.fillStyle = u, r.translate((t + i.gw / 2) * g * s, (n + i.gh / 2) * g * s), 0 !== d && r.rotate(-d), r.textBaseline = "middle", r.fillText(o, i.fillTextOffsetX * s, (i.fillTextOffsetY + .5 * m) * s), r.restore()
                        } else {
                            var l = document.createElement("span"), p = "";
                            p = "rotate(" + -d / Math.PI * 180 + "deg) ", 1 !== i.mu && (p += "translateX(-" + i.fillTextWidth / 4 + "px) scale(" + 1 / i.mu + ")");
                            var h = {
                                position: "absolute",
                                display: "block",
                                font: a.fontWeight + " " + m * i.mu + "px " + a.fontFamily,
                                left: (t + i.gw / 2) * g + i.fillTextOffsetX + "px",
                                top: (n + i.gh / 2) * g + i.fillTextOffsetY + "px",
                                width: i.fillTextWidth + "px",
                                height: i.fillTextHeight + "px",
                                lineHeight: m + "px",
                                whiteSpace: "nowrap",
                                transform: p,
                                webkitTransform: p,
                                msTransform: p,
                                transformOrigin: "50% 40%",
                                webkitTransformOrigin: "50% 40%",
                                msTransformOrigin: "50% 40%"
                            };
                            u && (h.color = u), l.textContent = o;
                            for (var f in h)l.style[f] = h[f];
                            if (c)for (var y in c)l.setAttribute(y, c[y]);
                            _ && (l.className += _), e.appendChild(l)
                        }
                    })
                }, C = function (t, n, a, i, o) {
                    if (!(t >= u || n >= m || t < 0 || n < 0)) {
                        if (c[t][n] = !1, a) {
                            var r = e[0].getContext("2d");
                            r.fillRect(t * g, n * g, y, y)
                        }
                        k && (L[t][n] = {item: o, dimension: i})
                    }
                }, E = function (t, n, i, o, r, s) {
                    var l, d = r.occupied, c = a.drawMask;
                    c && (l = e[0].getContext("2d"), l.save(), l.fillStyle = a.maskColor);
                    var _;
                    if (k) {
                        var p = r.bounds;
                        _ = {x: (t + p[3]) * g, y: (n + p[0]) * g, w: (p[1] - p[3] + 1) * g, h: (p[2] - p[0] + 1) * g}
                    }
                    for (var h = d.length; h--;) {
                        var f = t + d[h][0], y = n + d[h][1];
                        f >= u || y >= m || f < 0 || y < 0 || C(f, y, c, _, s)
                    }
                    c && l.restore()
                }, A = function (e) {
                    var t, n, i;
                    Array.isArray(e) ? (t = e[0], n = e[1]) : (t = e.word, n = e.weight, i = e.attributes);
                    var o = H(), r = j(t, n, o);
                    if (!r)return !1;
                    if (z())return !1;
                    if (!a.drawOutOfBound) {
                        var l = r.bounds;
                        if (l[1] - l[3] + 1 > u || l[2] - l[0] + 1 > m)return !1
                    }
                    for (var d = p + 1, c = function (a) {
                        var s = Math.floor(a[0] - r.gw / 2), l = Math.floor(a[1] - r.gh / 2), c = r.gw, u = r.gh;
                        return !!F(s, l, c, u, r.occupied) && (P(s, l, r, t, n, p - d, a[2], o, i), E(s, l, c, u, r, e), !0)
                    }; d--;) {
                        var _ = S(p - d);
                        a.shuffle && (_ = [].concat(_), s(_));
                        var h = _.some(c);
                        if (h)return !0
                    }
                    return !1
                }, B = function (t, n, a) {
                    return n ? !e.some(function (e) {
                        var i = document.createEvent("CustomEvent");
                        return i.initCustomEvent(t, !0, n, a || {}), !e.dispatchEvent(i)
                    }, this) : void e.forEach(function (e) {
                        var i = document.createEvent("CustomEvent");
                        i.initCustomEvent(t, !0, n, a || {}), e.dispatchEvent(i)
                    }, this)
                }, I = function () {
                    var t = e[0];
                    if (t.getContext)u = Math.ceil(t.width / g), m = Math.ceil(t.height / g); else {
                        var n = t.getBoundingClientRect();
                        u = Math.ceil(n.width / g), m = Math.ceil(n.height / g)
                    }
                    if (B("wordcloudstart", !0)) {
                        _ = a.origin ? [a.origin[0] / g, a.origin[1] / g] : [u / 2, m / 2], p = Math.floor(Math.sqrt(u * u + m * m)), c = [];
                        var i, o, r;
                        if (!t.getContext || a.clearCanvas)for (e.forEach(function (e) {
                            if (e.getContext) {
                                var t = e.getContext("2d");
                                t.fillStyle = a.backgroundColor, t.clearRect(0, 0, u * (g + 1), m * (g + 1)), t.fillRect(0, 0, u * (g + 1), m * (g + 1))
                            } else e.textContent = "", e.style.backgroundColor = a.backgroundColor, e.style.position = "relative"
                        }), i = u; i--;)for (c[i] = [], o = m; o--;)c[i][o] = !0; else {
                            var s = document.createElement("canvas").getContext("2d");
                            s.fillStyle = a.backgroundColor, s.fillRect(0, 0, 1, 1);
                            var l = s.getImageData(0, 0, 1, 1).data, d = t.getContext("2d").getImageData(0, 0, u * g, m * g).data;
                            i = u;
                            for (var f, y; i--;)for (c[i] = [], o = m; o--;) {
                                y = g;
                                e:for (; y--;)for (f = g; f--;)for (r = 4; r--;)if (d[4 * ((o * g + y) * u * g + (i * g + f)) + r] !== l[r]) {
                                    c[i][o] = !1;
                                    break e
                                }
                                c[i][o] !== !1 && (c[i][o] = !0)
                            }
                            d = s = l = void 0
                        }
                        if (a.hover || a.click) {
                            for (k = !0, i = u + 1; i--;)L[i] = [];
                            a.hover && t.addEventListener("mousemove", Y);
                            var v = function (e) {
                                e.preventDefault()
                            };
                            a.click && (t.addEventListener("click", D), t.addEventListener("touchstart", D), t.addEventListener("touchend", v), t.style.webkitTapHighlightColor = "rgba(0, 0, 0, 0)"), t.addEventListener("wordcloudstart", function j() {
                                t.removeEventListener("wordcloudstart", j), t.removeEventListener("mousemove", Y), t.removeEventListener("click", D), t.removeEventListener("touchstart", D), t.removeEventListener("touchend", v), M = void 0
                            })
                        }
                        r = 0;
                        var b, w;
                        0 !== a.wait ? (b = window.setTimeout, w = window.clearTimeout) : (b = window.setImmediate, w = window.clearImmediate);
                        var x = function (t, n) {
                            e.forEach(function (e) {
                                e.addEventListener(t, n)
                            }, this)
                        }, T = function (t, n) {
                            e.forEach(function (e) {
                                e.removeEventListener(t, n)
                            }, this)
                        }, S = function F() {
                            T("wordcloudstart", F), w(H)
                        };
                        x("wordcloudstart", S);
                        var H = b(function P() {
                            if (r >= a.list.length)return w(H), B("wordcloudstop", !1), void T("wordcloudstart", S);
                            h = (new Date).getTime();
                            var e = A(a.list[r]), t = !B("wordclouddrawn", !0, {item: a.list[r], drawn: e});
                            return z() || t ? (w(H), a.abort(), B("wordcloudabort", !1), B("wordcloudstop", !1), void T("wordcloudstart", S)) : (r++, void(H = b(P, a.wait)))
                        }, a.wait)
                    }
                };
                I()
            }
        };
        d.isSupported = o, d.minFontSize = r, n.WordCloud = d, a = [], i = function () {
            return d
        }.apply(t, a), !(void 0 !== i && (e.exports = i))
    }(this)
}, function (e, t, n) {
    const a = n(10).Fetch, i = (n(2), n(17)), o = n(16), r = (n(51), function (e, t) {
        var n = i.enc.Utf8.parse(t), a = null;
        try {
            a = i.DES.encrypt(JSON.stringify(e), n, {mode: i.mode.ECB, padding: i.pad.Pkcs7})
        } catch (o) {
            return
        }
        return a.toString()
    });
    e.exports = {
        insertRecord: function (e, t) {
            return a("/auth/invite/log", {body: e, method: "post"}, t)
        }, getRecord: function (e) {
            return a("/auth/invite/get", {body: null, method: "get"}, function (t) {
                if (t.err || !t.list)return t;
                const n = (new Date).getTime() + 31536e7, a = t.list;
                for (var i = 0, o = a.length; i < o; i++)new Date(a[i].vip_end_time).getTime() > n ? a[i].vip = !0 : a[i].vip = !1;
                t.list = a, e(t)
            })
        }, joinInvite: function (e, t) {
            return a("/auth/invite/join", {body: e, method: "post"}, t)
        }, isVip: function () {
            return vip
        }, getUserId: function () {
            return userid
        }, setVip: function (e) {
            vip = e
        }, login: function (e, t) {
            return a("/auth/user/login", {body: {k: e}, method: "post"}, t)
        }, register: function (e, t) {
            return a("/auth/user/reg", {body: {k: e}, method: "post"}, t)
        }, encryptCode: function (e, t, n) {
            return r(e, t)
        }, val: function (e, t, n) {
            return a("/auth/user/val", {body: {email: e, phone: t}, method: "post"}, n)
        }, getCookieToken: function () {
            return o.get("p") || ""
        }, loginByOther: function (e, t) {
            return a("/auth/user/platform", {body: {k: e}, method: "post"}, t)
        }, verifyCode: function (e, t) {
            return a("/auth/user/verifycode", {body: {k: e}, method: "post"}, t)
        }, uploadImage: function (e, t) {
            return a("/upload/img", {body: e, method: "post", attach: !0, server: "baca"}, t)
        }, uploadTemplate: function (e, t) {
            return a("/auth/template/upload", {body: e, method: "post"}, t)
        }, uploadCustomImage: function (e, t) {
            return a("/auth/template/customimage", {body: e, method: "post"}, t)
        }, saveImageTemplateSource: function (e, t) {
            return a("/logo/template/asy", {body: e, method: "post"}, t)
        }, resetPwd: function (e, t) {
            return a("/auth/user/resetpwd", {body: {k: e}, method: "post"}, t)
        }, splitWord: function (e, t) {
            return a("/ywordle/template/splitword", {body: {s: e}, method: "post"}, t)
        }
    }
}, function (e, t, n) {
    const a = n(11), i = n(16);
    var o = "https://api.pps.ymark.cc";
    window.location.href.indexOf("10.16.31.156") >= 0 && (o = "http://10.16.31.156:8360");
    const r = "http://idcrawler.vm.newsinpalm.net/magicadapi/yee";
    e.exports = {
        Fetch: function (e, t, n) {
            n = n || function () {
                }, e.indexOf("http") < 0 && (e = ("baca" == t.server ? r : o) + e);
            var s = Object.assign({}, {method: "get", url: "", body: {}, withCredentials: !0}, t), l = s.body, d = a;
            d = d[s.method.toLocaleLowerCase()](e), l && (d = "get" === s.method ? s.paramFilter ? d.query(JSON.stringify(l)) : d.query(l) : d.send(l)), d = s.attach ? d.set("enctype", "multipart/form-data") : d.type("form"), s.withCredentials && (d = d.withCredentials(!0)), d.end(function (t, a, o) {
                if (t)return d.abort(), console.error("请求接口：" + e + "时，遇到了问题1:"), n({err: t.toString()});
                if (a.error)return d.abort(), console.error("请求接口：" + e + "时，遇到了问题2:"), n({err: a.text});
                var r = JSON.parse(a.text);
                return "-99" == r.errno ? (console.log("fetch:-99,", e), d.abort()) : "-98" == r.errno ? (d.abort(), void alert("登录信息已经过期了，请重新登录！")) : r.errno < 0 ? (d.abort(), console.error("请求接口：" + e + "时，遇到了问题3:"), void n({
                    err: r.errmsg,
                    errno: r.errno,
                    data: r.data
                })) : ("undefined" != typeof r.data && (r = r.data), r.write_cookie && i.set("p", r.write_cookie, {
                    expires: 1,
                    path: "",
                    domain: ".yciyun.com"
                }), n(r))
            })
        }
    }
}, function (e, t, n) {
    function a() {
    }

    function i(e) {
        if (!g(e))return e;
        var t = [];
        for (var n in e)o(t, n, e[n]);
        return t.join("&")
    }

    function o(e, t, n) {
        if (null != n)if (Array.isArray(n))n.forEach(function (n) {
            o(e, t, n)
        }); else if (g(n))for (var a in n)o(e, t + "[" + a + "]", n[a]); else e.push(encodeURIComponent(t) + "=" + encodeURIComponent(n)); else null === n && e.push(encodeURIComponent(t))
    }

    function r(e) {
        for (var t, n, a = {}, i = e.split("&"), o = 0, r = i.length; o < r; ++o)t = i[o], n = t.indexOf("="), n == -1 ? a[decodeURIComponent(t)] = "" : a[decodeURIComponent(t.slice(0, n))] = decodeURIComponent(t.slice(n + 1));
        return a
    }

    function s(e) {
        var t, n, a, i, o = e.split(/\r?\n/), r = {};
        o.pop();
        for (var s = 0, l = o.length; s < l; ++s)n = o[s], t = n.indexOf(":"), a = n.slice(0, t).toLowerCase(), i = v(n.slice(t + 1)), r[a] = i;
        return r
    }

    function l(e) {
        return /[\/+]json\b/.test(e)
    }

    function d(e) {
        return e.split(/ *; */).shift()
    }

    function c(e) {
        return e.split(/ *; */).reduce(function (e, t) {
            var n = t.split(/ *= */), a = n.shift(), i = n.shift();
            return a && i && (e[a] = i), e
        }, {})
    }

    function u(e, t) {
        t = t || {}, this.req = e, this.xhr = this.req.xhr, this.text = "HEAD" != this.req.method && ("" === this.xhr.responseType || "text" === this.xhr.responseType) || "undefined" == typeof this.xhr.responseType ? this.xhr.responseText : null, this.statusText = this.req.xhr.statusText, this._setStatusProperties(this.xhr.status), this.header = this.headers = s(this.xhr.getAllResponseHeaders()), this.header["content-type"] = this.xhr.getResponseHeader("content-type"), this._setHeaderProperties(this.header), this.body = "HEAD" != this.req.method ? this._parseBody(this.text ? this.text : this.xhr.response) : null
    }

    function m(e, t) {
        var n = this;
        this._query = this._query || [], this.method = e, this.url = t, this.header = {}, this._header = {}, this.on("end", function () {
            var e = null, t = null;
            try {
                t = new u(n)
            } catch (a) {
                return e = new Error("Parser is unable to parse the response"), e.parse = !0, e.original = a, e.rawResponse = n.xhr && n.xhr.responseText ? n.xhr.responseText : null, e.statusCode = n.xhr && n.xhr.status ? n.xhr.status : null, n.callback(e)
            }
            n.emit("response", t);
            var i;
            try {
                (t.status < 200 || t.status >= 300) && (i = new Error(t.statusText || "Unsuccessful HTTP response"), i.original = e, i.response = t, i.status = t.status)
            } catch (a) {
                i = a
            }
            i ? n.callback(i, t) : n.callback(null, t)
        })
    }

    function _(e, t) {
        var n = y("DELETE", e);
        return t && n.end(t), n
    }

    var p;
    "undefined" != typeof window ? p = window : "undefined" != typeof self ? p = self : (console.warn("Using browser-only version of superagent in non-browser environment"), p = this);
    var h = n(12), f = n(13), g = n(14), y = e.exports = n(15).bind(null, m);
    y.getXHR = function () {
        if (!(!p.XMLHttpRequest || p.location && "file:" == p.location.protocol && p.ActiveXObject))return new XMLHttpRequest;
        try {
            return new ActiveXObject("Microsoft.XMLHTTP")
        } catch (e) {
        }
        try {
            return new ActiveXObject("Msxml2.XMLHTTP.6.0")
        } catch (e) {
        }
        try {
            return new ActiveXObject("Msxml2.XMLHTTP.3.0")
        } catch (e) {
        }
        try {
            return new ActiveXObject("Msxml2.XMLHTTP")
        } catch (e) {
        }
        throw Error("Browser-only verison of superagent could not find XHR")
    };
    var v = "".trim ? function (e) {
        return e.trim()
    } : function (e) {
        return e.replace(/(^\s*|\s*$)/g, "")
    };
    y.serializeObject = i, y.parseString = r, y.types = {
        html: "text/html",
        json: "application/json",
        xml: "application/xml",
        urlencoded: "application/x-www-form-urlencoded",
        form: "application/x-www-form-urlencoded",
        "form-data": "application/x-www-form-urlencoded"
    }, y.serialize = {
        "application/x-www-form-urlencoded": i,
        "application/json": JSON.stringify
    }, y.parse = {
        "application/x-www-form-urlencoded": r,
        "application/json": JSON.parse
    }, u.prototype.get = function (e) {
        return this.header[e.toLowerCase()]
    }, u.prototype._setHeaderProperties = function (e) {
        var t = this.header["content-type"] || "";
        this.type = d(t);
        var n = c(t);
        for (var a in n)this[a] = n[a]
    }, u.prototype._parseBody = function (e) {
        var t = y.parse[this.type];
        return !t && l(this.type) && (t = y.parse["application/json"]), t && e && (e.length || e instanceof Object) ? t(e) : null
    }, u.prototype._setStatusProperties = function (e) {
        1223 === e && (e = 204);
        var t = e / 100 | 0;
        this.status = this.statusCode = e, this.statusType = t, this.info = 1 == t, this.ok = 2 == t, this.clientError = 4 == t, this.serverError = 5 == t, this.error = (4 == t || 5 == t) && this.toError(), this.accepted = 202 == e, this.noContent = 204 == e, this.badRequest = 400 == e, this.unauthorized = 401 == e, this.notAcceptable = 406 == e, this.notFound = 404 == e, this.forbidden = 403 == e
    }, u.prototype.toError = function () {
        var e = this.req, t = e.method, n = e.url, a = "cannot " + t + " " + n + " (" + this.status + ")", i = new Error(a);
        return i.status = this.status, i.method = t, i.url = n, i
    }, y.Response = u, h(m.prototype);
    for (var b in f)m.prototype[b] = f[b];
    m.prototype.type = function (e) {
        return this.set("Content-Type", y.types[e] || e), this
    }, m.prototype.responseType = function (e) {
        return this._responseType = e, this
    }, m.prototype.accept = function (e) {
        return this.set("Accept", y.types[e] || e), this
    }, m.prototype.auth = function (e, t, n) {
        switch (n || (n = {type: "basic"}), n.type) {
            case"basic":
                var a = btoa(e + ":" + t);
                this.set("Authorization", "Basic " + a);
                break;
            case"auto":
                this.username = e, this.password = t
        }
        return this
    }, m.prototype.query = function (e) {
        return "string" != typeof e && (e = i(e)), e && this._query.push(e), this
    }, m.prototype.attach = function (e, t, n) {
        return this._getFormData().append(e, t, n || t.name), this
    }, m.prototype._getFormData = function () {
        return this._formData || (this._formData = new p.FormData), this._formData
    }, m.prototype.callback = function (e, t) {
        var n = this._callback;
        this.clearTimeout(), n(e, t)
    }, m.prototype.crossDomainError = function () {
        var e = new Error("Request has been terminated\nPossible causes: the network is offline, Origin is not allowed by Access-Control-Allow-Origin, the page is being unloaded, etc.");
        e.crossDomain = !0, e.status = this.status, e.method = this.method, e.url = this.url, this.callback(e)
    }, m.prototype._timeoutError = function () {
        var e = this._timeout, t = new Error("timeout of " + e + "ms exceeded");
        t.timeout = e, this.callback(t)
    }, m.prototype._appendQueryString = function () {
        var e = this._query.join("&");
        e && (this.url += ~this.url.indexOf("?") ? "&" + e : "?" + e)
    }, m.prototype.end = function (e) {
        var t = this, n = this.xhr = y.getXHR(), i = this._timeout, o = this._formData || this._data;
        this._callback = e || a, n.onreadystatechange = function () {
            if (4 == n.readyState) {
                var e;
                try {
                    e = n.status
                } catch (a) {
                    e = 0
                }
                if (0 == e) {
                    if (t.timedout)return t._timeoutError();
                    if (t._aborted)return;
                    return t.crossDomainError()
                }
                t.emit("end")
            }
        };
        var r = function (e, n) {
            n.total > 0 && (n.percent = n.loaded / n.total * 100), n.direction = e, t.emit("progress", n)
        };
        if (this.hasListeners("progress"))try {
            n.onprogress = r.bind(null, "download"), n.upload && (n.upload.onprogress = r.bind(null, "upload"))
        } catch (s) {
        }
        if (i && !this._timer && (this._timer = setTimeout(function () {
                t.timedout = !0, t.abort()
            }, i)), this._appendQueryString(), this.username && this.password ? n.open(this.method, this.url, !0, this.username, this.password) : n.open(this.method, this.url, !0), this._withCredentials && (n.withCredentials = !0), "GET" != this.method && "HEAD" != this.method && "string" != typeof o && !this._isHost(o)) {
            var d = this._header["content-type"], c = this._serializer || y.serialize[d ? d.split(";")[0] : ""];
            !c && l(d) && (c = y.serialize["application/json"]), c && (o = c(o))
        }
        for (var u in this.header)null != this.header[u] && n.setRequestHeader(u, this.header[u]);
        return this._responseType && (n.responseType = this._responseType), this.emit("request", this), n.send("undefined" != typeof o ? o : null), this
    }, y.Request = m, y.get = function (e, t, n) {
        var a = y("GET", e);
        return "function" == typeof t && (n = t, t = null), t && a.query(t), n && a.end(n), a
    }, y.head = function (e, t, n) {
        var a = y("HEAD", e);
        return "function" == typeof t && (n = t, t = null), t && a.send(t), n && a.end(n), a
    }, y.options = function (e, t, n) {
        var a = y("OPTIONS", e);
        return "function" == typeof t && (n = t, t = null), t && a.send(t), n && a.end(n), a
    }, y.del = _, y["delete"] = _, y.patch = function (e, t, n) {
        var a = y("PATCH", e);
        return "function" == typeof t && (n = t, t = null), t && a.send(t), n && a.end(n), a
    }, y.post = function (e, t, n) {
        var a = y("POST", e);
        return "function" == typeof t && (n = t, t = null), t && a.send(t), n && a.end(n), a
    }, y.put = function (e, t, n) {
        var a = y("PUT", e);
        return "function" == typeof t && (n = t, t = null), t && a.send(t), n && a.end(n), a
    }
}, function (e, t, n) {
    function a(e) {
        if (e)return i(e)
    }

    function i(e) {
        for (var t in a.prototype)e[t] = a.prototype[t];
        return e
    }

    e.exports = a, a.prototype.on = a.prototype.addEventListener = function (e, t) {
        return this._callbacks = this._callbacks || {}, (this._callbacks["$" + e] = this._callbacks["$" + e] || []).push(t), this
    }, a.prototype.once = function (e, t) {
        function n() {
            this.off(e, n), t.apply(this, arguments)
        }

        return n.fn = t, this.on(e, n), this
    }, a.prototype.off = a.prototype.removeListener = a.prototype.removeAllListeners = a.prototype.removeEventListener = function (e, t) {
        if (this._callbacks = this._callbacks || {}, 0 == arguments.length)return this._callbacks = {}, this;
        var n = this._callbacks["$" + e];
        if (!n)return this;
        if (1 == arguments.length)return delete this._callbacks["$" + e], this;
        for (var a, i = 0; i < n.length; i++)if (a = n[i], a === t || a.fn === t) {
            n.splice(i, 1);
            break
        }
        return this
    }, a.prototype.emit = function (e) {
        this._callbacks = this._callbacks || {};
        var t = [].slice.call(arguments, 1), n = this._callbacks["$" + e];
        if (n) {
            n = n.slice(0);
            for (var a = 0, i = n.length; a < i; ++a)n[a].apply(this, t)
        }
        return this
    }, a.prototype.listeners = function (e) {
        return this._callbacks = this._callbacks || {}, this._callbacks["$" + e] || []
    }, a.prototype.hasListeners = function (e) {
        return !!this.listeners(e).length
    }
}, function (e, t, n) {
    var a = n(14);
    t.clearTimeout = function () {
        return this._timeout = 0, clearTimeout(this._timer), this
    }, t.parse = function (e) {
        return this._parser = e, this
    }, t.serialize = function (e) {
        return this._serializer = e, this
    }, t.timeout = function (e) {
        return this._timeout = e, this
    }, t.then = function (e, t) {
        if (!this._fullfilledPromise) {
            var n = this;
            this._fullfilledPromise = new Promise(function (e, t) {
                n.end(function (n, a) {
                    n ? t(n) : e(a)
                })
            })
        }
        return this._fullfilledPromise.then(e, t)
    }, t["catch"] = function (e) {
        return this.then(void 0, e)
    }, t.use = function (e) {
        return e(this), this
    }, t.get = function (e) {
        return this._header[e.toLowerCase()]
    }, t.getHeader = t.get, t.set = function (e, t) {
        if (a(e)) {
            for (var n in e)this.set(n, e[n]);
            return this
        }
        return this._header[e.toLowerCase()] = t, this.header[e] = t, this
    }, t.unset = function (e) {
        return delete this._header[e.toLowerCase()], delete this.header[e], this
    }, t.field = function (e, t) {
        if (null === e || void 0 === e)throw new Error(".field(name, val) name can not be empty");
        if (a(e)) {
            for (var n in e)this.field(n, e[n]);
            return this
        }
        if (null === t || void 0 === t)throw new Error(".field(name, val) val can not be empty");
        return this._getFormData().append(e, t), this
    }, t.abort = function () {
        return this._aborted ? this : (this._aborted = !0, this.xhr && this.xhr.abort(), this.req && this.req.abort(), this.clearTimeout(), this.emit("abort"), this)
    }, t.withCredentials = function () {
        return this._withCredentials = !0, this
    }, t.redirects = function (e) {
        return this._maxRedirects = e, this
    }, t.toJSON = function () {
        return {method: this.method, url: this.url, data: this._data, headers: this._header}
    }, t._isHost = function (e) {
        var t = {}.toString.call(e);
        switch (t) {
            case"[object File]":
            case"[object Blob]":
            case"[object FormData]":
                return !0;
            default:
                return !1
        }
    }, t.send = function (e) {
        var t = a(e), n = this._header["content-type"];
        if (t && a(this._data))for (var i in e)this._data[i] = e[i]; else"string" == typeof e ? (n || this.type("form"), n = this._header["content-type"], "application/x-www-form-urlencoded" == n ? this._data = this._data ? this._data + "&" + e : e : this._data = (this._data || "") + e) : this._data = e;
        return !t || this._isHost(e) ? this : (n || this.type("json"), this)
    }
}, function (e, t) {
    function n(e) {
        return null !== e && "object" == typeof e
    }

    e.exports = n
}, function (e, t) {
    function n(e, t, n) {
        return "function" == typeof n ? new e("GET", t).end(n) : 2 == arguments.length ? new e("GET", t) : new e(t, n)
    }

    e.exports = n
}, function (e, t, n) {
    var a, i;
    !function (o) {
        var r = !1;
        if (a = o, i = "function" == typeof a ? a.call(t, n, t, e) : a, !(void 0 !== i && (e.exports = i)), r = !0, e.exports = o(), r = !0, !r) {
            var s = window.Cookies, l = window.Cookies = o();
            l.noConflict = function () {
                return window.Cookies = s, l
            }
        }
    }(function () {
        function e() {
            for (var e = 0, t = {}; e < arguments.length; e++) {
                var n = arguments[e];
                for (var a in n)t[a] = n[a]
            }
            return t
        }

        function t(n) {
            function a(t, i, o) {
                var r;
                if ("undefined" != typeof document) {
                    if (arguments.length > 1) {
                        if (o = e({path: "/"}, a.defaults, o), "number" == typeof o.expires) {
                            var s = new Date;
                            s.setMilliseconds(s.getMilliseconds() + 864e5 * o.expires), o.expires = s
                        }
                        o.expires = o.expires ? o.expires.toUTCString() : "";
                        try {
                            r = JSON.stringify(i), /^[\{\[]/.test(r) && (i = r)
                        } catch (l) {
                        }
                        i = n.write ? n.write(i, t) : encodeURIComponent(String(i)).replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g, decodeURIComponent), t = encodeURIComponent(String(t)), t = t.replace(/%(23|24|26|2B|5E|60|7C)/g, decodeURIComponent), t = t.replace(/[\(\)]/g, escape);
                        var d = "";
                        for (var c in o)o[c] && (d += "; " + c, o[c] !== !0 && (d += "=" + o[c]));
                        return document.cookie = t + "=" + i + d
                    }
                    t || (r = {});
                    for (var u = document.cookie ? document.cookie.split("; ") : [], m = /(%[0-9A-Z]{2})+/g, _ = 0; _ < u.length; _++) {
                        var p = u[_].split("="), h = p.slice(1).join("=");
                        this.json || '"' !== h.charAt(0) || (h = h.slice(1, -1));
                        try {
                            var f = p[0].replace(m, decodeURIComponent);
                            if (h = n.read ? n.read(h, f) : n(h, f) || h.replace(m, decodeURIComponent), this.json)try {
                                h = JSON.parse(h)
                            } catch (l) {
                            }
                            if (t === f) {
                                r = h;
                                break
                            }
                            t || (r[f] = h)
                        } catch (l) {
                        }
                    }
                    return r
                }
            }

            return a.set = a, a.get = function (e) {
                return a.call(a, e)
            }, a.getJSON = function () {
                return a.apply({json: !0}, [].slice.call(arguments))
            }, a.defaults = {}, a.remove = function (t, n) {
                a(t, "", e(n, {expires: -1}))
            }, a.withConverter = t, a
        }

        return t(function () {
        })
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(19), n(20), n(21), n(22), n(23), n(24), n(25), n(26), n(27), n(28), n(29), n(30), n(31), n(32), n(33), n(34), n(35), n(36), n(37), n(38), n(39), n(40), n(41), n(42), n(43), n(44), n(45), n(46), n(47), n(48), n(49), n(50))
    }(this, function (e) {
        return e
    })
}, function (e, t, n) {
    !function (n, a) {
        e.exports = t = a()
    }(this, function () {
        var e = e || function (e, t) {
                var n = Object.create || function () {
                        function e() {
                        }

                        return function (t) {
                            var n;
                            return e.prototype = t, n = new e, e.prototype = null, n
                        }
                    }(), a = {}, i = a.lib = {}, o = i.Base = function () {
                    return {
                        extend: function (e) {
                            var t = n(this);
                            return e && t.mixIn(e), t.hasOwnProperty("init") && this.init !== t.init || (t.init = function () {
                                t.$super.init.apply(this, arguments)
                            }), t.init.prototype = t, t.$super = this, t
                        }, create: function () {
                            var e = this.extend();
                            return e.init.apply(e, arguments), e
                        }, init: function () {
                        }, mixIn: function (e) {
                            for (var t in e)e.hasOwnProperty(t) && (this[t] = e[t]);
                            e.hasOwnProperty("toString") && (this.toString = e.toString)
                        }, clone: function () {
                            return this.init.prototype.extend(this)
                        }
                    }
                }(), r = i.WordArray = o.extend({
                    init: function (e, n) {
                        e = this.words = e || [], n != t ? this.sigBytes = n : this.sigBytes = 4 * e.length
                    }, toString: function (e) {
                        return (e || l).stringify(this)
                    }, concat: function (e) {
                        var t = this.words, n = e.words, a = this.sigBytes, i = e.sigBytes;
                        if (this.clamp(), a % 4)for (var o = 0; o < i; o++) {
                            var r = n[o >>> 2] >>> 24 - o % 4 * 8 & 255;
                            t[a + o >>> 2] |= r << 24 - (a + o) % 4 * 8
                        } else for (var o = 0; o < i; o += 4)t[a + o >>> 2] = n[o >>> 2];
                        return this.sigBytes += i, this
                    }, clamp: function () {
                        var t = this.words, n = this.sigBytes;
                        t[n >>> 2] &= 4294967295 << 32 - n % 4 * 8, t.length = e.ceil(n / 4)
                    }, clone: function () {
                        var e = o.clone.call(this);
                        return e.words = this.words.slice(0), e
                    }, random: function (t) {
                        for (var n, a = [], i = function (t) {
                            var t = t, n = 987654321, a = 4294967295;
                            return function () {
                                n = 36969 * (65535 & n) + (n >> 16) & a, t = 18e3 * (65535 & t) + (t >> 16) & a;
                                var i = (n << 16) + t & a;
                                return i /= 4294967296, i += .5, i * (e.random() > .5 ? 1 : -1)
                            }
                        }, o = 0; o < t; o += 4) {
                            var s = i(4294967296 * (n || e.random()));
                            n = 987654071 * s(), a.push(4294967296 * s() | 0)
                        }
                        return new r.init(a, t)
                    }
                }), s = a.enc = {}, l = s.Hex = {
                    stringify: function (e) {
                        for (var t = e.words, n = e.sigBytes, a = [], i = 0; i < n; i++) {
                            var o = t[i >>> 2] >>> 24 - i % 4 * 8 & 255;
                            a.push((o >>> 4).toString(16)), a.push((15 & o).toString(16))
                        }
                        return a.join("")
                    }, parse: function (e) {
                        for (var t = e.length, n = [], a = 0; a < t; a += 2)n[a >>> 3] |= parseInt(e.substr(a, 2), 16) << 24 - a % 8 * 4;
                        return new r.init(n, t / 2)
                    }
                }, d = s.Latin1 = {
                    stringify: function (e) {
                        for (var t = e.words, n = e.sigBytes, a = [], i = 0; i < n; i++) {
                            var o = t[i >>> 2] >>> 24 - i % 4 * 8 & 255;
                            a.push(String.fromCharCode(o))
                        }
                        return a.join("")
                    }, parse: function (e) {
                        for (var t = e.length, n = [], a = 0; a < t; a++)n[a >>> 2] |= (255 & e.charCodeAt(a)) << 24 - a % 4 * 8;
                        return new r.init(n, t)
                    }
                }, c = s.Utf8 = {
                    stringify: function (e) {
                        try {
                            return decodeURIComponent(escape(d.stringify(e)))
                        } catch (t) {
                            throw new Error("Malformed UTF-8 data")
                        }
                    }, parse: function (e) {
                        return d.parse(unescape(encodeURIComponent(e)))
                    }
                }, u = i.BufferedBlockAlgorithm = o.extend({
                    reset: function () {
                        this._data = new r.init, this._nDataBytes = 0
                    }, _append: function (e) {
                        "string" == typeof e && (e = c.parse(e)), this._data.concat(e), this._nDataBytes += e.sigBytes
                    }, _process: function (t) {
                        var n = this._data, a = n.words, i = n.sigBytes, o = this.blockSize, s = 4 * o, l = i / s;
                        l = t ? e.ceil(l) : e.max((0 | l) - this._minBufferSize, 0);
                        var d = l * o, c = e.min(4 * d, i);
                        if (d) {
                            for (var u = 0; u < d; u += o)this._doProcessBlock(a, u);
                            var m = a.splice(0, d);
                            n.sigBytes -= c
                        }
                        return new r.init(m, c)
                    }, clone: function () {
                        var e = o.clone.call(this);
                        return e._data = this._data.clone(), e
                    }, _minBufferSize: 0
                }), m = (i.Hasher = u.extend({
                    cfg: o.extend(), init: function (e) {
                        this.cfg = this.cfg.extend(e), this.reset()
                    }, reset: function () {
                        u.reset.call(this), this._doReset()
                    }, update: function (e) {
                        return this._append(e), this._process(), this
                    }, finalize: function (e) {
                        e && this._append(e);
                        var t = this._doFinalize();
                        return t
                    }, blockSize: 16, _createHelper: function (e) {
                        return function (t, n) {
                            return new e.init(n).finalize(t)
                        }
                    }, _createHmacHelper: function (e) {
                        return function (t, n) {
                            return new m.HMAC.init(e, n).finalize(t)
                        }
                    }
                }), a.algo = {});
                return a
            }(Math);
        return e
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        return function (t) {
            var n = e, a = n.lib, i = a.Base, o = a.WordArray, r = n.x64 = {};
            r.Word = i.extend({
                init: function (e, t) {
                    this.high = e, this.low = t
                }
            }), r.WordArray = i.extend({
                init: function (e, n) {
                    e = this.words = e || [], n != t ? this.sigBytes = n : this.sigBytes = 8 * e.length
                }, toX32: function () {
                    for (var e = this.words, t = e.length, n = [], a = 0; a < t; a++) {
                        var i = e[a];
                        n.push(i.high), n.push(i.low)
                    }
                    return o.create(n, this.sigBytes)
                }, clone: function () {
                    for (var e = i.clone.call(this), t = e.words = this.words.slice(0), n = t.length, a = 0; a < n; a++)t[a] = t[a].clone();
                    return e
                }
            })
        }(), e
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        return function () {
            if ("function" == typeof ArrayBuffer) {
                var t = e, n = t.lib, a = n.WordArray, i = a.init, o = a.init = function (e) {
                    if (e instanceof ArrayBuffer && (e = new Uint8Array(e)), (e instanceof Int8Array || "undefined" != typeof Uint8ClampedArray && e instanceof Uint8ClampedArray || e instanceof Int16Array || e instanceof Uint16Array || e instanceof Int32Array || e instanceof Uint32Array || e instanceof Float32Array || e instanceof Float64Array) && (e = new Uint8Array(e.buffer, e.byteOffset, e.byteLength)), e instanceof Uint8Array) {
                        for (var t = e.byteLength, n = [], a = 0; a < t; a++)n[a >>> 2] |= e[a] << 24 - a % 4 * 8;
                        i.call(this, n, t)
                    } else i.apply(this, arguments)
                };
                o.prototype = a
            }
        }(), e.lib.WordArray
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        return function () {
            function t(e) {
                return e << 8 & 4278255360 | e >>> 8 & 16711935
            }

            var n = e, a = n.lib, i = a.WordArray, o = n.enc;
            o.Utf16 = o.Utf16BE = {
                stringify: function (e) {
                    for (var t = e.words, n = e.sigBytes, a = [], i = 0; i < n; i += 2) {
                        var o = t[i >>> 2] >>> 16 - i % 4 * 8 & 65535;
                        a.push(String.fromCharCode(o))
                    }
                    return a.join("")
                }, parse: function (e) {
                    for (var t = e.length, n = [], a = 0; a < t; a++)n[a >>> 1] |= e.charCodeAt(a) << 16 - a % 2 * 16;
                    return i.create(n, 2 * t)
                }
            };
            o.Utf16LE = {
                stringify: function (e) {
                    for (var n = e.words, a = e.sigBytes, i = [], o = 0; o < a; o += 2) {
                        var r = t(n[o >>> 2] >>> 16 - o % 4 * 8 & 65535);
                        i.push(String.fromCharCode(r))
                    }
                    return i.join("")
                }, parse: function (e) {
                    for (var n = e.length, a = [], o = 0; o < n; o++)a[o >>> 1] |= t(e.charCodeAt(o) << 16 - o % 2 * 16);
                    return i.create(a, 2 * n)
                }
            }
        }(), e.enc.Utf16
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        return function () {
            function t(e, t, n) {
                for (var a = [], o = 0, r = 0; r < t; r++)if (r % 4) {
                    var s = n[e.charCodeAt(r - 1)] << r % 4 * 2, l = n[e.charCodeAt(r)] >>> 6 - r % 4 * 2;
                    a[o >>> 2] |= (s | l) << 24 - o % 4 * 8, o++
                }
                return i.create(a, o)
            }

            var n = e, a = n.lib, i = a.WordArray, o = n.enc;
            o.Base64 = {
                stringify: function (e) {
                    var t = e.words, n = e.sigBytes, a = this._map;
                    e.clamp();
                    for (var i = [], o = 0; o < n; o += 3)for (var r = t[o >>> 2] >>> 24 - o % 4 * 8 & 255, s = t[o + 1 >>> 2] >>> 24 - (o + 1) % 4 * 8 & 255, l = t[o + 2 >>> 2] >>> 24 - (o + 2) % 4 * 8 & 255, d = r << 16 | s << 8 | l, c = 0; c < 4 && o + .75 * c < n; c++)i.push(a.charAt(d >>> 6 * (3 - c) & 63));
                    var u = a.charAt(64);
                    if (u)for (; i.length % 4;)i.push(u);
                    return i.join("")
                }, parse: function (e) {
                    var n = e.length, a = this._map, i = this._reverseMap;
                    if (!i) {
                        i = this._reverseMap = [];
                        for (var o = 0; o < a.length; o++)i[a.charCodeAt(o)] = o
                    }
                    var r = a.charAt(64);
                    if (r) {
                        var s = e.indexOf(r);
                        s !== -1 && (n = s)
                    }
                    return t(e, n, i)
                }, _map: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
            }
        }(), e.enc.Base64
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        return function (t) {
            function n(e, t, n, a, i, o, r) {
                var s = e + (t & n | ~t & a) + i + r;
                return (s << o | s >>> 32 - o) + t
            }

            function a(e, t, n, a, i, o, r) {
                var s = e + (t & a | n & ~a) + i + r;
                return (s << o | s >>> 32 - o) + t
            }

            function i(e, t, n, a, i, o, r) {
                var s = e + (t ^ n ^ a) + i + r;
                return (s << o | s >>> 32 - o) + t
            }

            function o(e, t, n, a, i, o, r) {
                var s = e + (n ^ (t | ~a)) + i + r;
                return (s << o | s >>> 32 - o) + t
            }

            var r = e, s = r.lib, l = s.WordArray, d = s.Hasher, c = r.algo, u = [];
            !function () {
                for (var e = 0; e < 64; e++)u[e] = 4294967296 * t.abs(t.sin(e + 1)) | 0
            }();
            var m = c.MD5 = d.extend({
                _doReset: function () {
                    this._hash = new l.init([1732584193, 4023233417, 2562383102, 271733878])
                }, _doProcessBlock: function (e, t) {
                    for (var r = 0; r < 16; r++) {
                        var s = t + r, l = e[s];
                        e[s] = 16711935 & (l << 8 | l >>> 24) | 4278255360 & (l << 24 | l >>> 8)
                    }
                    var d = this._hash.words, c = e[t + 0], m = e[t + 1], _ = e[t + 2], p = e[t + 3], h = e[t + 4], f = e[t + 5], g = e[t + 6], y = e[t + 7], v = e[t + 8], b = e[t + 9], w = e[t + 10], M = e[t + 11], k = e[t + 12], L = e[t + 13], x = e[t + 14], Y = e[t + 15], D = d[0], T = d[1], S = d[2], z = d[3];
                    D = n(D, T, S, z, c, 7, u[0]), z = n(z, D, T, S, m, 12, u[1]), S = n(S, z, D, T, _, 17, u[2]), T = n(T, S, z, D, p, 22, u[3]), D = n(D, T, S, z, h, 7, u[4]), z = n(z, D, T, S, f, 12, u[5]), S = n(S, z, D, T, g, 17, u[6]), T = n(T, S, z, D, y, 22, u[7]), D = n(D, T, S, z, v, 7, u[8]), z = n(z, D, T, S, b, 12, u[9]), S = n(S, z, D, T, w, 17, u[10]), T = n(T, S, z, D, M, 22, u[11]), D = n(D, T, S, z, k, 7, u[12]), z = n(z, D, T, S, L, 12, u[13]), S = n(S, z, D, T, x, 17, u[14]), T = n(T, S, z, D, Y, 22, u[15]), D = a(D, T, S, z, m, 5, u[16]), z = a(z, D, T, S, g, 9, u[17]), S = a(S, z, D, T, M, 14, u[18]), T = a(T, S, z, D, c, 20, u[19]), D = a(D, T, S, z, f, 5, u[20]), z = a(z, D, T, S, w, 9, u[21]), S = a(S, z, D, T, Y, 14, u[22]), T = a(T, S, z, D, h, 20, u[23]), D = a(D, T, S, z, b, 5, u[24]), z = a(z, D, T, S, x, 9, u[25]), S = a(S, z, D, T, p, 14, u[26]), T = a(T, S, z, D, v, 20, u[27]), D = a(D, T, S, z, L, 5, u[28]), z = a(z, D, T, S, _, 9, u[29]), S = a(S, z, D, T, y, 14, u[30]), T = a(T, S, z, D, k, 20, u[31]), D = i(D, T, S, z, f, 4, u[32]), z = i(z, D, T, S, v, 11, u[33]), S = i(S, z, D, T, M, 16, u[34]), T = i(T, S, z, D, x, 23, u[35]), D = i(D, T, S, z, m, 4, u[36]), z = i(z, D, T, S, h, 11, u[37]), S = i(S, z, D, T, y, 16, u[38]), T = i(T, S, z, D, w, 23, u[39]), D = i(D, T, S, z, L, 4, u[40]), z = i(z, D, T, S, c, 11, u[41]), S = i(S, z, D, T, p, 16, u[42]), T = i(T, S, z, D, g, 23, u[43]), D = i(D, T, S, z, b, 4, u[44]), z = i(z, D, T, S, k, 11, u[45]), S = i(S, z, D, T, Y, 16, u[46]), T = i(T, S, z, D, _, 23, u[47]), D = o(D, T, S, z, c, 6, u[48]), z = o(z, D, T, S, y, 10, u[49]), S = o(S, z, D, T, x, 15, u[50]), T = o(T, S, z, D, f, 21, u[51]), D = o(D, T, S, z, k, 6, u[52]), z = o(z, D, T, S, p, 10, u[53]), S = o(S, z, D, T, w, 15, u[54]), T = o(T, S, z, D, m, 21, u[55]), D = o(D, T, S, z, v, 6, u[56]), z = o(z, D, T, S, Y, 10, u[57]), S = o(S, z, D, T, g, 15, u[58]), T = o(T, S, z, D, L, 21, u[59]), D = o(D, T, S, z, h, 6, u[60]), z = o(z, D, T, S, M, 10, u[61]), S = o(S, z, D, T, _, 15, u[62]), T = o(T, S, z, D, b, 21, u[63]), d[0] = d[0] + D | 0, d[1] = d[1] + T | 0, d[2] = d[2] + S | 0, d[3] = d[3] + z | 0
                }, _doFinalize: function () {
                    var e = this._data, n = e.words, a = 8 * this._nDataBytes, i = 8 * e.sigBytes;
                    n[i >>> 5] |= 128 << 24 - i % 32;
                    var o = t.floor(a / 4294967296), r = a;
                    n[(i + 64 >>> 9 << 4) + 15] = 16711935 & (o << 8 | o >>> 24) | 4278255360 & (o << 24 | o >>> 8), n[(i + 64 >>> 9 << 4) + 14] = 16711935 & (r << 8 | r >>> 24) | 4278255360 & (r << 24 | r >>> 8), e.sigBytes = 4 * (n.length + 1), this._process();
                    for (var s = this._hash, l = s.words, d = 0; d < 4; d++) {
                        var c = l[d];
                        l[d] = 16711935 & (c << 8 | c >>> 24) | 4278255360 & (c << 24 | c >>> 8)
                    }
                    return s
                }, clone: function () {
                    var e = d.clone.call(this);
                    return e._hash = this._hash.clone(), e
                }
            });
            r.MD5 = d._createHelper(m), r.HmacMD5 = d._createHmacHelper(m)
        }(Math), e.MD5
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        return function () {
            var t = e, n = t.lib, a = n.WordArray, i = n.Hasher, o = t.algo, r = [], s = o.SHA1 = i.extend({
                _doReset: function () {
                    this._hash = new a.init([1732584193, 4023233417, 2562383102, 271733878, 3285377520])
                }, _doProcessBlock: function (e, t) {
                    for (var n = this._hash.words, a = n[0], i = n[1], o = n[2], s = n[3], l = n[4], d = 0; d < 80; d++) {
                        if (d < 16)r[d] = 0 | e[t + d]; else {
                            var c = r[d - 3] ^ r[d - 8] ^ r[d - 14] ^ r[d - 16];
                            r[d] = c << 1 | c >>> 31
                        }
                        var u = (a << 5 | a >>> 27) + l + r[d];
                        u += d < 20 ? (i & o | ~i & s) + 1518500249 : d < 40 ? (i ^ o ^ s) + 1859775393 : d < 60 ? (i & o | i & s | o & s) - 1894007588 : (i ^ o ^ s) - 899497514, l = s, s = o, o = i << 30 | i >>> 2, i = a, a = u
                    }
                    n[0] = n[0] + a | 0, n[1] = n[1] + i | 0, n[2] = n[2] + o | 0, n[3] = n[3] + s | 0, n[4] = n[4] + l | 0
                }, _doFinalize: function () {
                    var e = this._data, t = e.words, n = 8 * this._nDataBytes, a = 8 * e.sigBytes;
                    return t[a >>> 5] |= 128 << 24 - a % 32, t[(a + 64 >>> 9 << 4) + 14] = Math.floor(n / 4294967296), t[(a + 64 >>> 9 << 4) + 15] = n, e.sigBytes = 4 * t.length, this._process(), this._hash
                }, clone: function () {
                    var e = i.clone.call(this);
                    return e._hash = this._hash.clone(), e
                }
            });
            t.SHA1 = i._createHelper(s), t.HmacSHA1 = i._createHmacHelper(s)
        }(), e.SHA1
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        return function (t) {
            var n = e, a = n.lib, i = a.WordArray, o = a.Hasher, r = n.algo, s = [], l = [];
            !function () {
                function e(e) {
                    for (var n = t.sqrt(e), a = 2; a <= n; a++)if (!(e % a))return !1;
                    return !0
                }

                function n(e) {
                    return 4294967296 * (e - (0 | e)) | 0
                }

                for (var a = 2, i = 0; i < 64;)e(a) && (i < 8 && (s[i] = n(t.pow(a, .5))), l[i] = n(t.pow(a, 1 / 3)), i++), a++
            }();
            var d = [], c = r.SHA256 = o.extend({
                _doReset: function () {
                    this._hash = new i.init(s.slice(0))
                }, _doProcessBlock: function (e, t) {
                    for (var n = this._hash.words, a = n[0], i = n[1], o = n[2], r = n[3], s = n[4], c = n[5], u = n[6], m = n[7], _ = 0; _ < 64; _++) {
                        if (_ < 16)d[_] = 0 | e[t + _]; else {
                            var p = d[_ - 15], h = (p << 25 | p >>> 7) ^ (p << 14 | p >>> 18) ^ p >>> 3, f = d[_ - 2], g = (f << 15 | f >>> 17) ^ (f << 13 | f >>> 19) ^ f >>> 10;
                            d[_] = h + d[_ - 7] + g + d[_ - 16]
                        }
                        var y = s & c ^ ~s & u, v = a & i ^ a & o ^ i & o, b = (a << 30 | a >>> 2) ^ (a << 19 | a >>> 13) ^ (a << 10 | a >>> 22), w = (s << 26 | s >>> 6) ^ (s << 21 | s >>> 11) ^ (s << 7 | s >>> 25), M = m + w + y + l[_] + d[_], k = b + v;
                        m = u, u = c, c = s, s = r + M | 0, r = o, o = i, i = a, a = M + k | 0
                    }
                    n[0] = n[0] + a | 0, n[1] = n[1] + i | 0, n[2] = n[2] + o | 0, n[3] = n[3] + r | 0, n[4] = n[4] + s | 0, n[5] = n[5] + c | 0, n[6] = n[6] + u | 0, n[7] = n[7] + m | 0
                }, _doFinalize: function () {
                    var e = this._data, n = e.words, a = 8 * this._nDataBytes, i = 8 * e.sigBytes;
                    return n[i >>> 5] |= 128 << 24 - i % 32, n[(i + 64 >>> 9 << 4) + 14] = t.floor(a / 4294967296), n[(i + 64 >>> 9 << 4) + 15] = a, e.sigBytes = 4 * n.length, this._process(), this._hash
                }, clone: function () {
                    var e = o.clone.call(this);
                    return e._hash = this._hash.clone(), e
                }
            });
            n.SHA256 = o._createHelper(c), n.HmacSHA256 = o._createHmacHelper(c)
        }(Math), e.SHA256
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(25))
    }(this, function (e) {
        return function () {
            var t = e, n = t.lib, a = n.WordArray, i = t.algo, o = i.SHA256, r = i.SHA224 = o.extend({
                _doReset: function () {
                    this._hash = new a.init([3238371032, 914150663, 812702999, 4144912697, 4290775857, 1750603025, 1694076839, 3204075428])
                }, _doFinalize: function () {
                    var e = o._doFinalize.call(this);
                    return e.sigBytes -= 4, e
                }
            });
            t.SHA224 = o._createHelper(r), t.HmacSHA224 = o._createHmacHelper(r)
        }(), e.SHA224
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(19))
    }(this, function (e) {
        return function () {
            function t() {
                return r.create.apply(r, arguments)
            }

            var n = e, a = n.lib, i = a.Hasher, o = n.x64, r = o.Word, s = o.WordArray, l = n.algo, d = [t(1116352408, 3609767458), t(1899447441, 602891725), t(3049323471, 3964484399), t(3921009573, 2173295548), t(961987163, 4081628472), t(1508970993, 3053834265), t(2453635748, 2937671579), t(2870763221, 3664609560), t(3624381080, 2734883394), t(310598401, 1164996542), t(607225278, 1323610764), t(1426881987, 3590304994), t(1925078388, 4068182383), t(2162078206, 991336113), t(2614888103, 633803317), t(3248222580, 3479774868), t(3835390401, 2666613458), t(4022224774, 944711139), t(264347078, 2341262773), t(604807628, 2007800933), t(770255983, 1495990901), t(1249150122, 1856431235), t(1555081692, 3175218132), t(1996064986, 2198950837), t(2554220882, 3999719339), t(2821834349, 766784016), t(2952996808, 2566594879), t(3210313671, 3203337956), t(3336571891, 1034457026), t(3584528711, 2466948901), t(113926993, 3758326383), t(338241895, 168717936), t(666307205, 1188179964), t(773529912, 1546045734), t(1294757372, 1522805485), t(1396182291, 2643833823), t(1695183700, 2343527390), t(1986661051, 1014477480), t(2177026350, 1206759142), t(2456956037, 344077627), t(2730485921, 1290863460), t(2820302411, 3158454273), t(3259730800, 3505952657), t(3345764771, 106217008), t(3516065817, 3606008344), t(3600352804, 1432725776), t(4094571909, 1467031594), t(275423344, 851169720), t(430227734, 3100823752), t(506948616, 1363258195), t(659060556, 3750685593), t(883997877, 3785050280), t(958139571, 3318307427), t(1322822218, 3812723403), t(1537002063, 2003034995), t(1747873779, 3602036899), t(1955562222, 1575990012), t(2024104815, 1125592928), t(2227730452, 2716904306), t(2361852424, 442776044), t(2428436474, 593698344), t(2756734187, 3733110249), t(3204031479, 2999351573), t(3329325298, 3815920427), t(3391569614, 3928383900), t(3515267271, 566280711), t(3940187606, 3454069534), t(4118630271, 4000239992), t(116418474, 1914138554), t(174292421, 2731055270), t(289380356, 3203993006), t(460393269, 320620315), t(685471733, 587496836), t(852142971, 1086792851), t(1017036298, 365543100), t(1126000580, 2618297676), t(1288033470, 3409855158), t(1501505948, 4234509866), t(1607167915, 987167468), t(1816402316, 1246189591)], c = [];
            !function () {
                for (var e = 0; e < 80; e++)c[e] = t()
            }();
            var u = l.SHA512 = i.extend({
                _doReset: function () {
                    this._hash = new s.init([new r.init(1779033703, 4089235720), new r.init(3144134277, 2227873595), new r.init(1013904242, 4271175723), new r.init(2773480762, 1595750129), new r.init(1359893119, 2917565137), new r.init(2600822924, 725511199), new r.init(528734635, 4215389547), new r.init(1541459225, 327033209)])
                }, _doProcessBlock: function (e, t) {
                    for (var n = this._hash.words, a = n[0], i = n[1], o = n[2], r = n[3], s = n[4], l = n[5], u = n[6], m = n[7], _ = a.high, p = a.low, h = i.high, f = i.low, g = o.high, y = o.low, v = r.high, b = r.low, w = s.high, M = s.low, k = l.high, L = l.low, x = u.high, Y = u.low, D = m.high, T = m.low, S = _, z = p, H = h, j = f, F = g, P = y, C = v, E = b, A = w, B = M, I = k, W = L, O = x, R = Y, N = D, V = T, J = 0; J < 80; J++) {
                        var U = c[J];
                        if (J < 16)var q = U.high = 0 | e[t + 2 * J], G = U.low = 0 | e[t + 2 * J + 1]; else {
                            var $ = c[J - 15], K = $.high, X = $.low, Z = (K >>> 1 | X << 31) ^ (K >>> 8 | X << 24) ^ K >>> 7, Q = (X >>> 1 | K << 31) ^ (X >>> 8 | K << 24) ^ (X >>> 7 | K << 25), ee = c[J - 2], te = ee.high, ne = ee.low, ae = (te >>> 19 | ne << 13) ^ (te << 3 | ne >>> 29) ^ te >>> 6, ie = (ne >>> 19 | te << 13) ^ (ne << 3 | te >>> 29) ^ (ne >>> 6 | te << 26), oe = c[J - 7], re = oe.high, se = oe.low, le = c[J - 16], de = le.high, ce = le.low, G = Q + se, q = Z + re + (G >>> 0 < Q >>> 0 ? 1 : 0), G = G + ie, q = q + ae + (G >>> 0 < ie >>> 0 ? 1 : 0), G = G + ce, q = q + de + (G >>> 0 < ce >>> 0 ? 1 : 0);
                            U.high = q, U.low = G
                        }
                        var ue = A & I ^ ~A & O, me = B & W ^ ~B & R, _e = S & H ^ S & F ^ H & F, pe = z & j ^ z & P ^ j & P, he = (S >>> 28 | z << 4) ^ (S << 30 | z >>> 2) ^ (S << 25 | z >>> 7), fe = (z >>> 28 | S << 4) ^ (z << 30 | S >>> 2) ^ (z << 25 | S >>> 7), ge = (A >>> 14 | B << 18) ^ (A >>> 18 | B << 14) ^ (A << 23 | B >>> 9), ye = (B >>> 14 | A << 18) ^ (B >>> 18 | A << 14) ^ (B << 23 | A >>> 9), ve = d[J], be = ve.high, we = ve.low, Me = V + ye, ke = N + ge + (Me >>> 0 < V >>> 0 ? 1 : 0), Me = Me + me, ke = ke + ue + (Me >>> 0 < me >>> 0 ? 1 : 0), Me = Me + we, ke = ke + be + (Me >>> 0 < we >>> 0 ? 1 : 0), Me = Me + G, ke = ke + q + (Me >>> 0 < G >>> 0 ? 1 : 0), Le = fe + pe, xe = he + _e + (Le >>> 0 < fe >>> 0 ? 1 : 0);
                        N = O, V = R, O = I, R = W, I = A, W = B, B = E + Me | 0, A = C + ke + (B >>> 0 < E >>> 0 ? 1 : 0) | 0, C = F, E = P, F = H, P = j, H = S, j = z, z = Me + Le | 0, S = ke + xe + (z >>> 0 < Me >>> 0 ? 1 : 0) | 0
                    }
                    p = a.low = p + z, a.high = _ + S + (p >>> 0 < z >>> 0 ? 1 : 0), f = i.low = f + j, i.high = h + H + (f >>> 0 < j >>> 0 ? 1 : 0), y = o.low = y + P, o.high = g + F + (y >>> 0 < P >>> 0 ? 1 : 0), b = r.low = b + E, r.high = v + C + (b >>> 0 < E >>> 0 ? 1 : 0), M = s.low = M + B, s.high = w + A + (M >>> 0 < B >>> 0 ? 1 : 0), L = l.low = L + W, l.high = k + I + (L >>> 0 < W >>> 0 ? 1 : 0), Y = u.low = Y + R, u.high = x + O + (Y >>> 0 < R >>> 0 ? 1 : 0), T = m.low = T + V, m.high = D + N + (T >>> 0 < V >>> 0 ? 1 : 0)
                }, _doFinalize: function () {
                    var e = this._data, t = e.words, n = 8 * this._nDataBytes, a = 8 * e.sigBytes;
                    t[a >>> 5] |= 128 << 24 - a % 32, t[(a + 128 >>> 10 << 5) + 30] = Math.floor(n / 4294967296), t[(a + 128 >>> 10 << 5) + 31] = n, e.sigBytes = 4 * t.length, this._process();
                    var i = this._hash.toX32();
                    return i
                }, clone: function () {
                    var e = i.clone.call(this);
                    return e._hash = this._hash.clone(), e
                }, blockSize: 32
            });
            n.SHA512 = i._createHelper(u), n.HmacSHA512 = i._createHmacHelper(u)
        }(), e.SHA512
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(19), n(27))
    }(this, function (e) {
        return function () {
            var t = e, n = t.x64, a = n.Word, i = n.WordArray, o = t.algo, r = o.SHA512, s = o.SHA384 = r.extend({
                _doReset: function () {
                    this._hash = new i.init([new a.init(3418070365, 3238371032), new a.init(1654270250, 914150663), new a.init(2438529370, 812702999), new a.init(355462360, 4144912697), new a.init(1731405415, 4290775857), new a.init(2394180231, 1750603025), new a.init(3675008525, 1694076839), new a.init(1203062813, 3204075428)])
                }, _doFinalize: function () {
                    var e = r._doFinalize.call(this);
                    return e.sigBytes -= 16, e
                }
            });
            t.SHA384 = r._createHelper(s), t.HmacSHA384 = r._createHmacHelper(s)
        }(), e.SHA384
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(19))
    }(this, function (e) {
        return function (t) {
            var n = e, a = n.lib, i = a.WordArray, o = a.Hasher, r = n.x64, s = r.Word, l = n.algo, d = [], c = [], u = [];
            !function () {
                for (var e = 1, t = 0, n = 0; n < 24; n++) {
                    d[e + 5 * t] = (n + 1) * (n + 2) / 2 % 64;
                    var a = t % 5, i = (2 * e + 3 * t) % 5;
                    e = a, t = i
                }
                for (var e = 0; e < 5; e++)for (var t = 0; t < 5; t++)c[e + 5 * t] = t + (2 * e + 3 * t) % 5 * 5;
                for (var o = 1, r = 0; r < 24; r++) {
                    for (var l = 0, m = 0, _ = 0; _ < 7; _++) {
                        if (1 & o) {
                            var p = (1 << _) - 1;
                            p < 32 ? m ^= 1 << p : l ^= 1 << p - 32
                        }
                        128 & o ? o = o << 1 ^ 113 : o <<= 1
                    }
                    u[r] = s.create(l, m)
                }
            }();
            var m = [];
            !function () {
                for (var e = 0; e < 25; e++)m[e] = s.create()
            }();
            var _ = l.SHA3 = o.extend({
                cfg: o.cfg.extend({outputLength: 512}), _doReset: function () {
                    for (var e = this._state = [], t = 0; t < 25; t++)e[t] = new s.init;
                    this.blockSize = (1600 - 2 * this.cfg.outputLength) / 32
                }, _doProcessBlock: function (e, t) {
                    for (var n = this._state, a = this.blockSize / 2, i = 0; i < a; i++) {
                        var o = e[t + 2 * i], r = e[t + 2 * i + 1];
                        o = 16711935 & (o << 8 | o >>> 24) | 4278255360 & (o << 24 | o >>> 8), r = 16711935 & (r << 8 | r >>> 24) | 4278255360 & (r << 24 | r >>> 8);
                        var s = n[i];
                        s.high ^= r, s.low ^= o
                    }
                    for (var l = 0; l < 24; l++) {
                        for (var _ = 0; _ < 5; _++) {
                            for (var p = 0, h = 0, f = 0; f < 5; f++) {
                                var s = n[_ + 5 * f];
                                p ^= s.high, h ^= s.low
                            }
                            var g = m[_];
                            g.high = p, g.low = h
                        }
                        for (var _ = 0; _ < 5; _++)for (var y = m[(_ + 4) % 5], v = m[(_ + 1) % 5], b = v.high, w = v.low, p = y.high ^ (b << 1 | w >>> 31), h = y.low ^ (w << 1 | b >>> 31), f = 0; f < 5; f++) {
                            var s = n[_ + 5 * f];
                            s.high ^= p, s.low ^= h
                        }
                        for (var M = 1; M < 25; M++) {
                            var s = n[M], k = s.high, L = s.low, x = d[M];
                            if (x < 32)var p = k << x | L >>> 32 - x, h = L << x | k >>> 32 - x; else var p = L << x - 32 | k >>> 64 - x, h = k << x - 32 | L >>> 64 - x;
                            var Y = m[c[M]];
                            Y.high = p, Y.low = h
                        }
                        var D = m[0], T = n[0];
                        D.high = T.high, D.low = T.low;
                        for (var _ = 0; _ < 5; _++)for (var f = 0; f < 5; f++) {
                            var M = _ + 5 * f, s = n[M], S = m[M], z = m[(_ + 1) % 5 + 5 * f], H = m[(_ + 2) % 5 + 5 * f];
                            s.high = S.high ^ ~z.high & H.high, s.low = S.low ^ ~z.low & H.low
                        }
                        var s = n[0], j = u[l];
                        s.high ^= j.high, s.low ^= j.low
                    }
                }, _doFinalize: function () {
                    var e = this._data, n = e.words, a = (8 * this._nDataBytes, 8 * e.sigBytes), o = 32 * this.blockSize;
                    n[a >>> 5] |= 1 << 24 - a % 32, n[(t.ceil((a + 1) / o) * o >>> 5) - 1] |= 128, e.sigBytes = 4 * n.length, this._process();
                    for (var r = this._state, s = this.cfg.outputLength / 8, l = s / 8, d = [], c = 0; c < l; c++) {
                        var u = r[c], m = u.high, _ = u.low;
                        m = 16711935 & (m << 8 | m >>> 24) | 4278255360 & (m << 24 | m >>> 8), _ = 16711935 & (_ << 8 | _ >>> 24) | 4278255360 & (_ << 24 | _ >>> 8), d.push(_), d.push(m)
                    }
                    return new i.init(d, s)
                }, clone: function () {
                    for (var e = o.clone.call(this), t = e._state = this._state.slice(0), n = 0; n < 25; n++)t[n] = t[n].clone();
                    return e
                }
            });
            n.SHA3 = o._createHelper(_), n.HmacSHA3 = o._createHmacHelper(_)
        }(Math), e.SHA3
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {/** @preserve
     (c) 2012 by Cédric Mesnil. All rights reserved.

     Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

     - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
     - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

     THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */
        return function (t) {
            function n(e, t, n) {
                return e ^ t ^ n
            }

            function a(e, t, n) {
                return e & t | ~e & n
            }

            function i(e, t, n) {
                return (e | ~t) ^ n
            }

            function o(e, t, n) {
                return e & n | t & ~n
            }

            function r(e, t, n) {
                return e ^ (t | ~n)
            }

            function s(e, t) {
                return e << t | e >>> 32 - t
            }

            var l = e, d = l.lib, c = d.WordArray, u = d.Hasher, m = l.algo, _ = c.create([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8, 3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2, 4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13]), p = c.create([5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2, 15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14, 12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11]), h = c.create([11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12, 9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6]), f = c.create([8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8, 8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11]), g = c.create([0, 1518500249, 1859775393, 2400959708, 2840853838]), y = c.create([1352829926, 1548603684, 1836072691, 2053994217, 0]), v = m.RIPEMD160 = u.extend({
                _doReset: function () {
                    this._hash = c.create([1732584193, 4023233417, 2562383102, 271733878, 3285377520])
                }, _doProcessBlock: function (e, t) {
                    for (var l = 0; l < 16; l++) {
                        var d = t + l, c = e[d];
                        e[d] = 16711935 & (c << 8 | c >>> 24) | 4278255360 & (c << 24 | c >>> 8)
                    }
                    var u, m, v, b, w, M, k, L, x, Y, D = this._hash.words, T = g.words, S = y.words, z = _.words, H = p.words, j = h.words, F = f.words;
                    M = u = D[0], k = m = D[1], L = v = D[2], x = b = D[3], Y = w = D[4];
                    for (var P, l = 0; l < 80; l += 1)P = u + e[t + z[l]] | 0, P += l < 16 ? n(m, v, b) + T[0] : l < 32 ? a(m, v, b) + T[1] : l < 48 ? i(m, v, b) + T[2] : l < 64 ? o(m, v, b) + T[3] : r(m, v, b) + T[4], P = 0 | P, P = s(P, j[l]), P = P + w | 0, u = w, w = b, b = s(v, 10), v = m, m = P, P = M + e[t + H[l]] | 0, P += l < 16 ? r(k, L, x) + S[0] : l < 32 ? o(k, L, x) + S[1] : l < 48 ? i(k, L, x) + S[2] : l < 64 ? a(k, L, x) + S[3] : n(k, L, x) + S[4], P = 0 | P, P = s(P, F[l]), P = P + Y | 0, M = Y, Y = x, x = s(L, 10), L = k, k = P;
                    P = D[1] + v + x | 0, D[1] = D[2] + b + Y | 0, D[2] = D[3] + w + M | 0, D[3] = D[4] + u + k | 0, D[4] = D[0] + m + L | 0, D[0] = P
                }, _doFinalize: function () {
                    var e = this._data, t = e.words, n = 8 * this._nDataBytes, a = 8 * e.sigBytes;
                    t[a >>> 5] |= 128 << 24 - a % 32, t[(a + 64 >>> 9 << 4) + 14] = 16711935 & (n << 8 | n >>> 24) | 4278255360 & (n << 24 | n >>> 8), e.sigBytes = 4 * (t.length + 1), this._process();
                    for (var i = this._hash, o = i.words, r = 0; r < 5; r++) {
                        var s = o[r];
                        o[r] = 16711935 & (s << 8 | s >>> 24) | 4278255360 & (s << 24 | s >>> 8)
                    }
                    return i
                }, clone: function () {
                    var e = u.clone.call(this);
                    return e._hash = this._hash.clone(), e
                }
            });
            l.RIPEMD160 = u._createHelper(v), l.HmacRIPEMD160 = u._createHmacHelper(v)
        }(Math), e.RIPEMD160
    })
}, function (e, t, n) {
    !function (a, i) {
        e.exports = t = i(n(18))
    }(this, function (e) {
        !function () {
            var t = e, n = t.lib, a = n.Base, i = t.enc, o = i.Utf8, r = t.algo;
            r.HMAC = a.extend({
                init: function (e, t) {
                    e = this._hasher = new e.init, "string" == typeof t && (t = o.parse(t));
                    var n = e.blockSize, a = 4 * n;
                    t.sigBytes > a && (t = e.finalize(t)), t.clamp();
                    for (var i = this._oKey = t.clone(), r = this._iKey = t.clone(), s = i.words, l = r.words, d = 0; d < n; d++)s[d] ^= 1549556828, l[d] ^= 909522486;
                    i.sigBytes = r.sigBytes = a, this.reset()
                }, reset: function () {
                    var e = this._hasher;
                    e.reset(), e.update(this._iKey)
                }, update: function (e) {
                    return this._hasher.update(e), this
                }, finalize: function (e) {
                    var t = this._hasher, n = t.finalize(e);
                    t.reset();
                    var a = t.finalize(this._oKey.clone().concat(n));
                    return a
                }
            })
        }()
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(24), n(31))
    }(this, function (e) {
        return function () {
            var t = e, n = t.lib, a = n.Base, i = n.WordArray, o = t.algo, r = o.SHA1, s = o.HMAC, l = o.PBKDF2 = a.extend({
                cfg: a.extend({
                    keySize: 4,
                    hasher: r,
                    iterations: 1
                }), init: function (e) {
                    this.cfg = this.cfg.extend(e)
                }, compute: function (e, t) {
                    for (var n = this.cfg, a = s.create(n.hasher, e), o = i.create(), r = i.create([1]), l = o.words, d = r.words, c = n.keySize, u = n.iterations; l.length < c;) {
                        var m = a.update(t).finalize(r);
                        a.reset();
                        for (var _ = m.words, p = _.length, h = m, f = 1; f < u; f++) {
                            h = a.finalize(h), a.reset();
                            for (var g = h.words, y = 0; y < p; y++)_[y] ^= g[y]
                        }
                        o.concat(m), d[0]++
                    }
                    return o.sigBytes = 4 * c, o
                }
            });
            t.PBKDF2 = function (e, t, n) {
                return l.create(n).compute(e, t)
            }
        }(), e.PBKDF2
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(24), n(31))
    }(this, function (e) {
        return function () {
            var t = e, n = t.lib, a = n.Base, i = n.WordArray, o = t.algo, r = o.MD5, s = o.EvpKDF = a.extend({
                cfg: a.extend({
                    keySize: 4,
                    hasher: r,
                    iterations: 1
                }), init: function (e) {
                    this.cfg = this.cfg.extend(e)
                }, compute: function (e, t) {
                    for (var n = this.cfg, a = n.hasher.create(), o = i.create(), r = o.words, s = n.keySize, l = n.iterations; r.length < s;) {
                        d && a.update(d);
                        var d = a.update(e).finalize(t);
                        a.reset();
                        for (var c = 1; c < l; c++)d = a.finalize(d), a.reset();
                        o.concat(d)
                    }
                    return o.sigBytes = 4 * s, o
                }
            });
            t.EvpKDF = function (e, t, n) {
                return s.create(n).compute(e, t)
            }
        }(), e.EvpKDF
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(33))
    }(this, function (e) {
        e.lib.Cipher || function (t) {
            var n = e, a = n.lib, i = a.Base, o = a.WordArray, r = a.BufferedBlockAlgorithm, s = n.enc, l = (s.Utf8, s.Base64), d = n.algo, c = d.EvpKDF, u = a.Cipher = r.extend({
                cfg: i.extend(),
                createEncryptor: function (e, t) {
                    return this.create(this._ENC_XFORM_MODE, e, t)
                },
                createDecryptor: function (e, t) {
                    return this.create(this._DEC_XFORM_MODE, e, t)
                },
                init: function (e, t, n) {
                    this.cfg = this.cfg.extend(n), this._xformMode = e, this._key = t, this.reset()
                },
                reset: function () {
                    r.reset.call(this), this._doReset()
                },
                process: function (e) {
                    return this._append(e), this._process()
                },
                finalize: function (e) {
                    e && this._append(e);
                    var t = this._doFinalize();
                    return t
                },
                keySize: 4,
                ivSize: 4,
                _ENC_XFORM_MODE: 1,
                _DEC_XFORM_MODE: 2,
                _createHelper: function () {
                    function e(e) {
                        return "string" == typeof e ? k : b
                    }

                    return function (t) {
                        return {
                            encrypt: function (n, a, i) {
                                return e(a).encrypt(t, n, a, i)
                            }, decrypt: function (n, a, i) {
                                return e(a).decrypt(t, n, a, i)
                            }
                        }
                    }
                }()
            }), m = (a.StreamCipher = u.extend({
                _doFinalize: function () {
                    var e = this._process(!0);
                    return e
                }, blockSize: 1
            }), n.mode = {}), _ = a.BlockCipherMode = i.extend({
                createEncryptor: function (e, t) {
                    return this.Encryptor.create(e, t)
                }, createDecryptor: function (e, t) {
                    return this.Decryptor.create(e, t)
                }, init: function (e, t) {
                    this._cipher = e, this._iv = t
                }
            }), p = m.CBC = function () {
                function e(e, n, a) {
                    var i = this._iv;
                    if (i) {
                        var o = i;
                        this._iv = t
                    } else var o = this._prevBlock;
                    for (var r = 0; r < a; r++)e[n + r] ^= o[r]
                }

                var n = _.extend();
                return n.Encryptor = n.extend({
                    processBlock: function (t, n) {
                        var a = this._cipher, i = a.blockSize;
                        e.call(this, t, n, i), a.encryptBlock(t, n), this._prevBlock = t.slice(n, n + i)
                    }
                }), n.Decryptor = n.extend({
                    processBlock: function (t, n) {
                        var a = this._cipher, i = a.blockSize, o = t.slice(n, n + i);
                        a.decryptBlock(t, n), e.call(this, t, n, i), this._prevBlock = o
                    }
                }), n
            }(), h = n.pad = {}, f = h.Pkcs7 = {
                pad: function (e, t) {
                    for (var n = 4 * t, a = n - e.sigBytes % n, i = a << 24 | a << 16 | a << 8 | a, r = [], s = 0; s < a; s += 4)r.push(i);
                    var l = o.create(r, a);
                    e.concat(l)
                }, unpad: function (e) {
                    var t = 255 & e.words[e.sigBytes - 1 >>> 2];
                    e.sigBytes -= t
                }
            }, g = (a.BlockCipher = u.extend({
                cfg: u.cfg.extend({mode: p, padding: f}), reset: function () {
                    u.reset.call(this);
                    var e = this.cfg, t = e.iv, n = e.mode;
                    if (this._xformMode == this._ENC_XFORM_MODE)var a = n.createEncryptor; else {
                        var a = n.createDecryptor;
                        this._minBufferSize = 1
                    }
                    this._mode && this._mode.__creator == a ? this._mode.init(this, t && t.words) : (this._mode = a.call(n, this, t && t.words), this._mode.__creator = a)
                }, _doProcessBlock: function (e, t) {
                    this._mode.processBlock(e, t)
                }, _doFinalize: function () {
                    var e = this.cfg.padding;
                    if (this._xformMode == this._ENC_XFORM_MODE) {
                        e.pad(this._data, this.blockSize);
                        var t = this._process(!0)
                    } else {
                        var t = this._process(!0);
                        e.unpad(t)
                    }
                    return t
                }, blockSize: 4
            }), a.CipherParams = i.extend({
                init: function (e) {
                    this.mixIn(e)
                }, toString: function (e) {
                    return (e || this.formatter).stringify(this)
                }
            })), y = n.format = {}, v = y.OpenSSL = {
                stringify: function (e) {
                    var t = e.ciphertext, n = e.salt;
                    if (n)var a = o.create([1398893684, 1701076831]).concat(n).concat(t); else var a = t;
                    return a.toString(l)
                }, parse: function (e) {
                    var t = l.parse(e), n = t.words;
                    if (1398893684 == n[0] && 1701076831 == n[1]) {
                        var a = o.create(n.slice(2, 4));
                        n.splice(0, 4), t.sigBytes -= 16
                    }
                    return g.create({ciphertext: t, salt: a})
                }
            }, b = a.SerializableCipher = i.extend({
                cfg: i.extend({format: v}), encrypt: function (e, t, n, a) {
                    a = this.cfg.extend(a);
                    var i = e.createEncryptor(n, a), o = i.finalize(t), r = i.cfg;
                    return g.create({
                        ciphertext: o,
                        key: n,
                        iv: r.iv,
                        algorithm: e,
                        mode: r.mode,
                        padding: r.padding,
                        blockSize: e.blockSize,
                        formatter: a.format
                    })
                }, decrypt: function (e, t, n, a) {
                    a = this.cfg.extend(a), t = this._parse(t, a.format);
                    var i = e.createDecryptor(n, a).finalize(t.ciphertext);
                    return i
                }, _parse: function (e, t) {
                    return "string" == typeof e ? t.parse(e, this) : e
                }
            }), w = n.kdf = {}, M = w.OpenSSL = {
                execute: function (e, t, n, a) {
                    a || (a = o.random(8));
                    var i = c.create({keySize: t + n}).compute(e, a), r = o.create(i.words.slice(t), 4 * n);
                    return i.sigBytes = 4 * t, g.create({key: i, iv: r, salt: a})
                }
            }, k = a.PasswordBasedCipher = b.extend({
                cfg: b.cfg.extend({kdf: M}), encrypt: function (e, t, n, a) {
                    a = this.cfg.extend(a);
                    var i = a.kdf.execute(n, e.keySize, e.ivSize);
                    a.iv = i.iv;
                    var o = b.encrypt.call(this, e, t, i.key, a);
                    return o.mixIn(i), o
                }, decrypt: function (e, t, n, a) {
                    a = this.cfg.extend(a), t = this._parse(t, a.format);
                    var i = a.kdf.execute(n, e.keySize, e.ivSize, t.salt);
                    a.iv = i.iv;
                    var o = b.decrypt.call(this, e, t, i.key, a);
                    return o
                }
            })
        }()
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.mode.CFB = function () {
            function t(e, t, n, a) {
                var i = this._iv;
                if (i) {
                    var o = i.slice(0);
                    this._iv = void 0
                } else var o = this._prevBlock;
                a.encryptBlock(o, 0);
                for (var r = 0; r < n; r++)e[t + r] ^= o[r]
            }

            var n = e.lib.BlockCipherMode.extend();
            return n.Encryptor = n.extend({
                processBlock: function (e, n) {
                    var a = this._cipher, i = a.blockSize;
                    t.call(this, e, n, i, a), this._prevBlock = e.slice(n, n + i)
                }
            }), n.Decryptor = n.extend({
                processBlock: function (e, n) {
                    var a = this._cipher, i = a.blockSize, o = e.slice(n, n + i);
                    t.call(this, e, n, i, a), this._prevBlock = o
                }
            }), n
        }(), e.mode.CFB
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.mode.CTR = function () {
            var t = e.lib.BlockCipherMode.extend(), n = t.Encryptor = t.extend({
                processBlock: function (e, t) {
                    var n = this._cipher, a = n.blockSize, i = this._iv, o = this._counter;
                    i && (o = this._counter = i.slice(0), this._iv = void 0);
                    var r = o.slice(0);
                    n.encryptBlock(r, 0), o[a - 1] = o[a - 1] + 1 | 0;
                    for (var s = 0; s < a; s++)e[t + s] ^= r[s]
                }
            });
            return t.Decryptor = n, t
        }(), e.mode.CTR
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {/** @preserve
     * Counter block mode compatible with  Dr Brian Gladman fileenc.c
     * derived from CryptoJS.mode.CTR
     * Jan Hruby jhruby.web@gmail.com
     */
        return e.mode.CTRGladman = function () {
            function t(e) {
                if (255 === (e >> 24 & 255)) {
                    var t = e >> 16 & 255, n = e >> 8 & 255, a = 255 & e;
                    255 === t ? (t = 0, 255 === n ? (n = 0, 255 === a ? a = 0 : ++a) : ++n) : ++t, e = 0, e += t << 16, e += n << 8, e += a
                } else e += 1 << 24;
                return e
            }

            function n(e) {
                return 0 === (e[0] = t(e[0])) && (e[1] = t(e[1])), e
            }

            var a = e.lib.BlockCipherMode.extend(), i = a.Encryptor = a.extend({
                processBlock: function (e, t) {
                    var a = this._cipher, i = a.blockSize, o = this._iv, r = this._counter;
                    o && (r = this._counter = o.slice(0), this._iv = void 0), n(r);
                    var s = r.slice(0);
                    a.encryptBlock(s, 0);
                    for (var l = 0; l < i; l++)e[t + l] ^= s[l]
                }
            });
            return a.Decryptor = i, a
        }(), e.mode.CTRGladman
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.mode.OFB = function () {
            var t = e.lib.BlockCipherMode.extend(), n = t.Encryptor = t.extend({
                processBlock: function (e, t) {
                    var n = this._cipher, a = n.blockSize, i = this._iv, o = this._keystream;
                    i && (o = this._keystream = i.slice(0), this._iv = void 0), n.encryptBlock(o, 0);
                    for (var r = 0; r < a; r++)e[t + r] ^= o[r]
                }
            });
            return t.Decryptor = n, t
        }(), e.mode.OFB
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.mode.ECB = function () {
            var t = e.lib.BlockCipherMode.extend();
            return t.Encryptor = t.extend({
                processBlock: function (e, t) {
                    this._cipher.encryptBlock(e, t)
                }
            }), t.Decryptor = t.extend({
                processBlock: function (e, t) {
                    this._cipher.decryptBlock(e, t)
                }
            }), t
        }(), e.mode.ECB
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.pad.AnsiX923 = {
            pad: function (e, t) {
                var n = e.sigBytes, a = 4 * t, i = a - n % a, o = n + i - 1;
                e.clamp(), e.words[o >>> 2] |= i << 24 - o % 4 * 8, e.sigBytes += i
            }, unpad: function (e) {
                var t = 255 & e.words[e.sigBytes - 1 >>> 2];
                e.sigBytes -= t
            }
        }, e.pad.Ansix923
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.pad.Iso10126 = {
            pad: function (t, n) {
                var a = 4 * n, i = a - t.sigBytes % a;
                t.concat(e.lib.WordArray.random(i - 1)).concat(e.lib.WordArray.create([i << 24], 1))
            }, unpad: function (e) {
                var t = 255 & e.words[e.sigBytes - 1 >>> 2];
                e.sigBytes -= t
            }
        }, e.pad.Iso10126
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.pad.Iso97971 = {
            pad: function (t, n) {
                t.concat(e.lib.WordArray.create([2147483648], 1)), e.pad.ZeroPadding.pad(t, n)
            }, unpad: function (t) {
                e.pad.ZeroPadding.unpad(t), t.sigBytes--
            }
        }, e.pad.Iso97971
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.pad.ZeroPadding = {
            pad: function (e, t) {
                var n = 4 * t;
                e.clamp(), e.sigBytes += n - (e.sigBytes % n || n)
            }, unpad: function (e) {
                for (var t = e.words, n = e.sigBytes - 1; !(t[n >>> 2] >>> 24 - n % 4 * 8 & 255);)n--;
                e.sigBytes = n + 1
            }
        }, e.pad.ZeroPadding
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return e.pad.NoPadding = {
            pad: function () {
            }, unpad: function () {
            }
        }, e.pad.NoPadding
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(34))
    }(this, function (e) {
        return function (t) {
            var n = e, a = n.lib, i = a.CipherParams, o = n.enc, r = o.Hex, s = n.format;
            s.Hex = {
                stringify: function (e) {
                    return e.ciphertext.toString(r)
                }, parse: function (e) {
                    var t = r.parse(e);
                    return i.create({ciphertext: t})
                }
            }
        }(), e.format.Hex
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(22), n(23), n(33), n(34))
    }(this, function (e) {
        return function () {
            var t = e, n = t.lib, a = n.BlockCipher, i = t.algo, o = [], r = [], s = [], l = [], d = [], c = [], u = [], m = [], _ = [], p = [];
            !function () {
                for (var e = [], t = 0; t < 256; t++)t < 128 ? e[t] = t << 1 : e[t] = t << 1 ^ 283;
                for (var n = 0, a = 0, t = 0; t < 256; t++) {
                    var i = a ^ a << 1 ^ a << 2 ^ a << 3 ^ a << 4;
                    i = i >>> 8 ^ 255 & i ^ 99, o[n] = i, r[i] = n;
                    var h = e[n], f = e[h], g = e[f], y = 257 * e[i] ^ 16843008 * i;
                    s[n] = y << 24 | y >>> 8, l[n] = y << 16 | y >>> 16, d[n] = y << 8 | y >>> 24, c[n] = y;
                    var y = 16843009 * g ^ 65537 * f ^ 257 * h ^ 16843008 * n;
                    u[i] = y << 24 | y >>> 8, m[i] = y << 16 | y >>> 16, _[i] = y << 8 | y >>> 24, p[i] = y, n ? (n = h ^ e[e[e[g ^ h]]], a ^= e[e[a]]) : n = a = 1
                }
            }();
            var h = [0, 1, 2, 4, 8, 16, 32, 64, 128, 27, 54], f = i.AES = a.extend({
                _doReset: function () {
                    if (!this._nRounds || this._keyPriorReset !== this._key) {
                        for (var e = this._keyPriorReset = this._key, t = e.words, n = e.sigBytes / 4, a = this._nRounds = n + 6, i = 4 * (a + 1), r = this._keySchedule = [], s = 0; s < i; s++)if (s < n)r[s] = t[s]; else {
                            var l = r[s - 1];
                            s % n ? n > 6 && s % n == 4 && (l = o[l >>> 24] << 24 | o[l >>> 16 & 255] << 16 | o[l >>> 8 & 255] << 8 | o[255 & l]) : (l = l << 8 | l >>> 24, l = o[l >>> 24] << 24 | o[l >>> 16 & 255] << 16 | o[l >>> 8 & 255] << 8 | o[255 & l], l ^= h[s / n | 0] << 24), r[s] = r[s - n] ^ l
                        }
                        for (var d = this._invKeySchedule = [], c = 0; c < i; c++) {
                            var s = i - c;
                            if (c % 4)var l = r[s]; else var l = r[s - 4];
                            c < 4 || s <= 4 ? d[c] = l : d[c] = u[o[l >>> 24]] ^ m[o[l >>> 16 & 255]] ^ _[o[l >>> 8 & 255]] ^ p[o[255 & l]]
                        }
                    }
                }, encryptBlock: function (e, t) {
                    this._doCryptBlock(e, t, this._keySchedule, s, l, d, c, o)
                }, decryptBlock: function (e, t) {
                    var n = e[t + 1];
                    e[t + 1] = e[t + 3], e[t + 3] = n, this._doCryptBlock(e, t, this._invKeySchedule, u, m, _, p, r);
                    var n = e[t + 1];
                    e[t + 1] = e[t + 3], e[t + 3] = n
                }, _doCryptBlock: function (e, t, n, a, i, o, r, s) {
                    for (var l = this._nRounds, d = e[t] ^ n[0], c = e[t + 1] ^ n[1], u = e[t + 2] ^ n[2], m = e[t + 3] ^ n[3], _ = 4, p = 1; p < l; p++) {
                        var h = a[d >>> 24] ^ i[c >>> 16 & 255] ^ o[u >>> 8 & 255] ^ r[255 & m] ^ n[_++], f = a[c >>> 24] ^ i[u >>> 16 & 255] ^ o[m >>> 8 & 255] ^ r[255 & d] ^ n[_++], g = a[u >>> 24] ^ i[m >>> 16 & 255] ^ o[d >>> 8 & 255] ^ r[255 & c] ^ n[_++], y = a[m >>> 24] ^ i[d >>> 16 & 255] ^ o[c >>> 8 & 255] ^ r[255 & u] ^ n[_++];
                        d = h, c = f, u = g, m = y
                    }
                    var h = (s[d >>> 24] << 24 | s[c >>> 16 & 255] << 16 | s[u >>> 8 & 255] << 8 | s[255 & m]) ^ n[_++], f = (s[c >>> 24] << 24 | s[u >>> 16 & 255] << 16 | s[m >>> 8 & 255] << 8 | s[255 & d]) ^ n[_++], g = (s[u >>> 24] << 24 | s[m >>> 16 & 255] << 16 | s[d >>> 8 & 255] << 8 | s[255 & c]) ^ n[_++], y = (s[m >>> 24] << 24 | s[d >>> 16 & 255] << 16 | s[c >>> 8 & 255] << 8 | s[255 & u]) ^ n[_++];
                    e[t] = h, e[t + 1] = f, e[t + 2] = g, e[t + 3] = y
                }, keySize: 8
            });
            t.AES = a._createHelper(f)
        }(), e.AES
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(22), n(23), n(33), n(34))
    }(this, function (e) {
        return function () {
            function t(e, t) {
                var n = (this._lBlock >>> e ^ this._rBlock) & t;
                this._rBlock ^= n, this._lBlock ^= n << e
            }

            function n(e, t) {
                var n = (this._rBlock >>> e ^ this._lBlock) & t;
                this._lBlock ^= n, this._rBlock ^= n << e
            }

            var a = e, i = a.lib, o = i.WordArray, r = i.BlockCipher, s = a.algo, l = [57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4], d = [14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32], c = [1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28], u = [{
                0: 8421888,
                268435456: 32768,
                536870912: 8421378,
                805306368: 2,
                1073741824: 512,
                1342177280: 8421890,
                1610612736: 8389122,
                1879048192: 8388608,
                2147483648: 514,
                2415919104: 8389120,
                2684354560: 33280,
                2952790016: 8421376,
                3221225472: 32770,
                3489660928: 8388610,
                3758096384: 0,
                4026531840: 33282,
                134217728: 0,
                402653184: 8421890,
                671088640: 33282,
                939524096: 32768,
                1207959552: 8421888,
                1476395008: 512,
                1744830464: 8421378,
                2013265920: 2,
                2281701376: 8389120,
                2550136832: 33280,
                2818572288: 8421376,
                3087007744: 8389122,
                3355443200: 8388610,
                3623878656: 32770,
                3892314112: 514,
                4160749568: 8388608,
                1: 32768,
                268435457: 2,
                536870913: 8421888,
                805306369: 8388608,
                1073741825: 8421378,
                1342177281: 33280,
                1610612737: 512,
                1879048193: 8389122,
                2147483649: 8421890,
                2415919105: 8421376,
                2684354561: 8388610,
                2952790017: 33282,
                3221225473: 514,
                3489660929: 8389120,
                3758096385: 32770,
                4026531841: 0,
                134217729: 8421890,
                402653185: 8421376,
                671088641: 8388608,
                939524097: 512,
                1207959553: 32768,
                1476395009: 8388610,
                1744830465: 2,
                2013265921: 33282,
                2281701377: 32770,
                2550136833: 8389122,
                2818572289: 514,
                3087007745: 8421888,
                3355443201: 8389120,
                3623878657: 0,
                3892314113: 33280,
                4160749569: 8421378
            }, {
                0: 1074282512,
                16777216: 16384,
                33554432: 524288,
                50331648: 1074266128,
                67108864: 1073741840,
                83886080: 1074282496,
                100663296: 1073758208,
                117440512: 16,
                134217728: 540672,
                150994944: 1073758224,
                167772160: 1073741824,
                184549376: 540688,
                201326592: 524304,
                218103808: 0,
                234881024: 16400,
                251658240: 1074266112,
                8388608: 1073758208,
                25165824: 540688,
                41943040: 16,
                58720256: 1073758224,
                75497472: 1074282512,
                92274688: 1073741824,
                109051904: 524288,
                125829120: 1074266128,
                142606336: 524304,
                159383552: 0,
                176160768: 16384,
                192937984: 1074266112,
                209715200: 1073741840,
                226492416: 540672,
                243269632: 1074282496,
                260046848: 16400,
                268435456: 0,
                285212672: 1074266128,
                301989888: 1073758224,
                318767104: 1074282496,
                335544320: 1074266112,
                352321536: 16,
                369098752: 540688,
                385875968: 16384,
                402653184: 16400,
                419430400: 524288,
                436207616: 524304,
                452984832: 1073741840,
                469762048: 540672,
                486539264: 1073758208,
                503316480: 1073741824,
                520093696: 1074282512,
                276824064: 540688,
                293601280: 524288,
                310378496: 1074266112,
                327155712: 16384,
                343932928: 1073758208,
                360710144: 1074282512,
                377487360: 16,
                394264576: 1073741824,
                411041792: 1074282496,
                427819008: 1073741840,
                444596224: 1073758224,
                461373440: 524304,
                478150656: 0,
                494927872: 16400,
                511705088: 1074266128,
                528482304: 540672
            }, {
                0: 260,
                1048576: 0,
                2097152: 67109120,
                3145728: 65796,
                4194304: 65540,
                5242880: 67108868,
                6291456: 67174660,
                7340032: 67174400,
                8388608: 67108864,
                9437184: 67174656,
                10485760: 65792,
                11534336: 67174404,
                12582912: 67109124,
                13631488: 65536,
                14680064: 4,
                15728640: 256,
                524288: 67174656,
                1572864: 67174404,
                2621440: 0,
                3670016: 67109120,
                4718592: 67108868,
                5767168: 65536,
                6815744: 65540,
                7864320: 260,
                8912896: 4,
                9961472: 256,
                11010048: 67174400,
                12058624: 65796,
                13107200: 65792,
                14155776: 67109124,
                15204352: 67174660,
                16252928: 67108864,
                16777216: 67174656,
                17825792: 65540,
                18874368: 65536,
                19922944: 67109120,
                20971520: 256,
                22020096: 67174660,
                23068672: 67108868,
                24117248: 0,
                25165824: 67109124,
                26214400: 67108864,
                27262976: 4,
                28311552: 65792,
                29360128: 67174400,
                30408704: 260,
                31457280: 65796,
                32505856: 67174404,
                17301504: 67108864,
                18350080: 260,
                19398656: 67174656,
                20447232: 0,
                21495808: 65540,
                22544384: 67109120,
                23592960: 256,
                24641536: 67174404,
                25690112: 65536,
                26738688: 67174660,
                27787264: 65796,
                28835840: 67108868,
                29884416: 67109124,
                30932992: 67174400,
                31981568: 4,
                33030144: 65792
            }, {
                0: 2151682048,
                65536: 2147487808,
                131072: 4198464,
                196608: 2151677952,
                262144: 0,
                327680: 4198400,
                393216: 2147483712,
                458752: 4194368,
                524288: 2147483648,
                589824: 4194304,
                655360: 64,
                720896: 2147487744,
                786432: 2151678016,
                851968: 4160,
                917504: 4096,
                983040: 2151682112,
                32768: 2147487808,
                98304: 64,
                163840: 2151678016,
                229376: 2147487744,
                294912: 4198400,
                360448: 2151682112,
                425984: 0,
                491520: 2151677952,
                557056: 4096,
                622592: 2151682048,
                688128: 4194304,
                753664: 4160,
                819200: 2147483648,
                884736: 4194368,
                950272: 4198464,
                1015808: 2147483712,
                1048576: 4194368,
                1114112: 4198400,
                1179648: 2147483712,
                1245184: 0,
                1310720: 4160,
                1376256: 2151678016,
                1441792: 2151682048,
                1507328: 2147487808,
                1572864: 2151682112,
                1638400: 2147483648,
                1703936: 2151677952,
                1769472: 4198464,
                1835008: 2147487744,
                1900544: 4194304,
                1966080: 64,
                2031616: 4096,
                1081344: 2151677952,
                1146880: 2151682112,
                1212416: 0,
                1277952: 4198400,
                1343488: 4194368,
                1409024: 2147483648,
                1474560: 2147487808,
                1540096: 64,
                1605632: 2147483712,
                1671168: 4096,
                1736704: 2147487744,
                1802240: 2151678016,
                1867776: 4160,
                1933312: 2151682048,
                1998848: 4194304,
                2064384: 4198464
            }, {
                0: 128,
                4096: 17039360,
                8192: 262144,
                12288: 536870912,
                16384: 537133184,
                20480: 16777344,
                24576: 553648256,
                28672: 262272,
                32768: 16777216,
                36864: 537133056,
                40960: 536871040,
                45056: 553910400,
                49152: 553910272,
                53248: 0,
                57344: 17039488,
                61440: 553648128,
                2048: 17039488,
                6144: 553648256,
                10240: 128,
                14336: 17039360,
                18432: 262144,
                22528: 537133184,
                26624: 553910272,
                30720: 536870912,
                34816: 537133056,
                38912: 0,
                43008: 553910400,
                47104: 16777344,
                51200: 536871040,
                55296: 553648128,
                59392: 16777216,
                63488: 262272,
                65536: 262144,
                69632: 128,
                73728: 536870912,
                77824: 553648256,
                81920: 16777344,
                86016: 553910272,
                90112: 537133184,
                94208: 16777216,
                98304: 553910400,
                102400: 553648128,
                106496: 17039360,
                110592: 537133056,
                114688: 262272,
                118784: 536871040,
                122880: 0,
                126976: 17039488,
                67584: 553648256,
                71680: 16777216,
                75776: 17039360,
                79872: 537133184,
                83968: 536870912,
                88064: 17039488,
                92160: 128,
                96256: 553910272,
                100352: 262272,
                104448: 553910400,
                108544: 0,
                112640: 553648128,
                116736: 16777344,
                120832: 262144,
                124928: 537133056,
                129024: 536871040
            }, {
                0: 268435464,
                256: 8192,
                512: 270532608,
                768: 270540808,
                1024: 268443648,
                1280: 2097152,
                1536: 2097160,
                1792: 268435456,
                2048: 0,
                2304: 268443656,
                2560: 2105344,
                2816: 8,
                3072: 270532616,
                3328: 2105352,
                3584: 8200,
                3840: 270540800,
                128: 270532608,
                384: 270540808,
                640: 8,
                896: 2097152,
                1152: 2105352,
                1408: 268435464,
                1664: 268443648,
                1920: 8200,
                2176: 2097160,
                2432: 8192,
                2688: 268443656,
                2944: 270532616,
                3200: 0,
                3456: 270540800,
                3712: 2105344,
                3968: 268435456,
                4096: 268443648,
                4352: 270532616,
                4608: 270540808,
                4864: 8200,
                5120: 2097152,
                5376: 268435456,
                5632: 268435464,
                5888: 2105344,
                6144: 2105352,
                6400: 0,
                6656: 8,
                6912: 270532608,
                7168: 8192,
                7424: 268443656,
                7680: 270540800,
                7936: 2097160,
                4224: 8,
                4480: 2105344,
                4736: 2097152,
                4992: 268435464,
                5248: 268443648,
                5504: 8200,
                5760: 270540808,
                6016: 270532608,
                6272: 270540800,
                6528: 270532616,
                6784: 8192,
                7040: 2105352,
                7296: 2097160,
                7552: 0,
                7808: 268435456,
                8064: 268443656
            }, {
                0: 1048576,
                16: 33555457,
                32: 1024,
                48: 1049601,
                64: 34604033,
                80: 0,
                96: 1,
                112: 34603009,
                128: 33555456,
                144: 1048577,
                160: 33554433,
                176: 34604032,
                192: 34603008,
                208: 1025,
                224: 1049600,
                240: 33554432,
                8: 34603009,
                24: 0,
                40: 33555457,
                56: 34604032,
                72: 1048576,
                88: 33554433,
                104: 33554432,
                120: 1025,
                136: 1049601,
                152: 33555456,
                168: 34603008,
                184: 1048577,
                200: 1024,
                216: 34604033,
                232: 1,
                248: 1049600,
                256: 33554432,
                272: 1048576,
                288: 33555457,
                304: 34603009,
                320: 1048577,
                336: 33555456,
                352: 34604032,
                368: 1049601,
                384: 1025,
                400: 34604033,
                416: 1049600,
                432: 1,
                448: 0,
                464: 34603008,
                480: 33554433,
                496: 1024,
                264: 1049600,
                280: 33555457,
                296: 34603009,
                312: 1,
                328: 33554432,
                344: 1048576,
                360: 1025,
                376: 34604032,
                392: 33554433,
                408: 34603008,
                424: 0,
                440: 34604033,
                456: 1049601,
                472: 1024,
                488: 33555456,
                504: 1048577
            }, {
                0: 134219808,
                1: 131072,
                2: 134217728,
                3: 32,
                4: 131104,
                5: 134350880,
                6: 134350848,
                7: 2048,
                8: 134348800,
                9: 134219776,
                10: 133120,
                11: 134348832,
                12: 2080,
                13: 0,
                14: 134217760,
                15: 133152,
                2147483648: 2048,
                2147483649: 134350880,
                2147483650: 134219808,
                2147483651: 134217728,
                2147483652: 134348800,
                2147483653: 133120,
                2147483654: 133152,
                2147483655: 32,
                2147483656: 134217760,
                2147483657: 2080,
                2147483658: 131104,
                2147483659: 134350848,
                2147483660: 0,
                2147483661: 134348832,
                2147483662: 134219776,
                2147483663: 131072,
                16: 133152,
                17: 134350848,
                18: 32,
                19: 2048,
                20: 134219776,
                21: 134217760,
                22: 134348832,
                23: 131072,
                24: 0,
                25: 131104,
                26: 134348800,
                27: 134219808,
                28: 134350880,
                29: 133120,
                30: 2080,
                31: 134217728,
                2147483664: 131072,
                2147483665: 2048,
                2147483666: 134348832,
                2147483667: 133152,
                2147483668: 32,
                2147483669: 134348800,
                2147483670: 134217728,
                2147483671: 134219808,
                2147483672: 134350880,
                2147483673: 134217760,
                2147483674: 134219776,
                2147483675: 0,
                2147483676: 133120,
                2147483677: 2080,
                2147483678: 131104,
                2147483679: 134350848
            }], m = [4160749569, 528482304, 33030144, 2064384, 129024, 8064, 504, 2147483679], _ = s.DES = r.extend({
                _doReset: function () {
                    for (var e = this._key, t = e.words, n = [], a = 0; a < 56; a++) {
                        var i = l[a] - 1;
                        n[a] = t[i >>> 5] >>> 31 - i % 32 & 1
                    }
                    for (var o = this._subKeys = [], r = 0; r < 16; r++) {
                        for (var s = o[r] = [], u = c[r], a = 0; a < 24; a++)s[a / 6 | 0] |= n[(d[a] - 1 + u) % 28] << 31 - a % 6, s[4 + (a / 6 | 0)] |= n[28 + (d[a + 24] - 1 + u) % 28] << 31 - a % 6;
                        s[0] = s[0] << 1 | s[0] >>> 31;
                        for (var a = 1; a < 7; a++)s[a] = s[a] >>> 4 * (a - 1) + 3;
                        s[7] = s[7] << 5 | s[7] >>> 27
                    }
                    for (var m = this._invSubKeys = [], a = 0; a < 16; a++)m[a] = o[15 - a]
                }, encryptBlock: function (e, t) {
                    this._doCryptBlock(e, t, this._subKeys)
                }, decryptBlock: function (e, t) {
                    this._doCryptBlock(e, t, this._invSubKeys)
                }, _doCryptBlock: function (e, a, i) {
                    this._lBlock = e[a], this._rBlock = e[a + 1], t.call(this, 4, 252645135), t.call(this, 16, 65535), n.call(this, 2, 858993459), n.call(this, 8, 16711935), t.call(this, 1, 1431655765);
                    for (var o = 0; o < 16; o++) {
                        for (var r = i[o], s = this._lBlock, l = this._rBlock, d = 0, c = 0; c < 8; c++)d |= u[c][((l ^ r[c]) & m[c]) >>> 0];
                        this._lBlock = l, this._rBlock = s ^ d
                    }
                    var _ = this._lBlock;
                    this._lBlock = this._rBlock, this._rBlock = _, t.call(this, 1, 1431655765), n.call(this, 8, 16711935), n.call(this, 2, 858993459), t.call(this, 16, 65535), t.call(this, 4, 252645135), e[a] = this._lBlock, e[a + 1] = this._rBlock
                }, keySize: 2, ivSize: 2, blockSize: 2
            });
            a.DES = r._createHelper(_);
            var p = s.TripleDES = r.extend({
                _doReset: function () {
                    var e = this._key, t = e.words;
                    this._des1 = _.createEncryptor(o.create(t.slice(0, 2))), this._des2 = _.createEncryptor(o.create(t.slice(2, 4))), this._des3 = _.createEncryptor(o.create(t.slice(4, 6)))
                }, encryptBlock: function (e, t) {
                    this._des1.encryptBlock(e, t), this._des2.decryptBlock(e, t), this._des3.encryptBlock(e, t)
                }, decryptBlock: function (e, t) {
                    this._des3.decryptBlock(e, t), this._des2.encryptBlock(e, t), this._des1.decryptBlock(e, t)
                }, keySize: 6, ivSize: 2, blockSize: 2
            });
            a.TripleDES = r._createHelper(p)
        }(), e.TripleDES
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(22), n(23), n(33), n(34))
    }(this, function (e) {
        return function () {
            function t() {
                for (var e = this._S, t = this._i, n = this._j, a = 0, i = 0; i < 4; i++) {
                    t = (t + 1) % 256, n = (n + e[t]) % 256;
                    var o = e[t];
                    e[t] = e[n], e[n] = o, a |= e[(e[t] + e[n]) % 256] << 24 - 8 * i
                }
                return this._i = t, this._j = n, a
            }

            var n = e, a = n.lib, i = a.StreamCipher, o = n.algo, r = o.RC4 = i.extend({
                _doReset: function () {
                    for (var e = this._key, t = e.words, n = e.sigBytes, a = this._S = [], i = 0; i < 256; i++)a[i] = i;
                    for (var i = 0, o = 0; i < 256; i++) {
                        var r = i % n, s = t[r >>> 2] >>> 24 - r % 4 * 8 & 255;
                        o = (o + a[i] + s) % 256;
                        var l = a[i];
                        a[i] = a[o], a[o] = l
                    }
                    this._i = this._j = 0
                }, _doProcessBlock: function (e, n) {
                    e[n] ^= t.call(this)
                }, keySize: 8, ivSize: 0
            });
            n.RC4 = i._createHelper(r);
            var s = o.RC4Drop = r.extend({
                cfg: r.cfg.extend({drop: 192}), _doReset: function () {
                    r._doReset.call(this);
                    for (var e = this.cfg.drop; e > 0; e--)t.call(this)
                }
            });
            n.RC4Drop = i._createHelper(s)
        }(), e.RC4
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(22), n(23), n(33), n(34))
    }(this, function (e) {
        return function () {
            function t() {
                for (var e = this._X, t = this._C, n = 0; n < 8; n++)s[n] = t[n];
                t[0] = t[0] + 1295307597 + this._b | 0, t[1] = t[1] + 3545052371 + (t[0] >>> 0 < s[0] >>> 0 ? 1 : 0) | 0, t[2] = t[2] + 886263092 + (t[1] >>> 0 < s[1] >>> 0 ? 1 : 0) | 0, t[3] = t[3] + 1295307597 + (t[2] >>> 0 < s[2] >>> 0 ? 1 : 0) | 0, t[4] = t[4] + 3545052371 + (t[3] >>> 0 < s[3] >>> 0 ? 1 : 0) | 0, t[5] = t[5] + 886263092 + (t[4] >>> 0 < s[4] >>> 0 ? 1 : 0) | 0, t[6] = t[6] + 1295307597 + (t[5] >>> 0 < s[5] >>> 0 ? 1 : 0) | 0, t[7] = t[7] + 3545052371 + (t[6] >>> 0 < s[6] >>> 0 ? 1 : 0) | 0, this._b = t[7] >>> 0 < s[7] >>> 0 ? 1 : 0;
                for (var n = 0; n < 8; n++) {
                    var a = e[n] + t[n], i = 65535 & a, o = a >>> 16, r = ((i * i >>> 17) + i * o >>> 15) + o * o, d = ((4294901760 & a) * a | 0) + ((65535 & a) * a | 0);
                    l[n] = r ^ d
                }
                e[0] = l[0] + (l[7] << 16 | l[7] >>> 16) + (l[6] << 16 | l[6] >>> 16) | 0, e[1] = l[1] + (l[0] << 8 | l[0] >>> 24) + l[7] | 0, e[2] = l[2] + (l[1] << 16 | l[1] >>> 16) + (l[0] << 16 | l[0] >>> 16) | 0, e[3] = l[3] + (l[2] << 8 | l[2] >>> 24) + l[1] | 0, e[4] = l[4] + (l[3] << 16 | l[3] >>> 16) + (l[2] << 16 | l[2] >>> 16) | 0, e[5] = l[5] + (l[4] << 8 | l[4] >>> 24) + l[3] | 0, e[6] = l[6] + (l[5] << 16 | l[5] >>> 16) + (l[4] << 16 | l[4] >>> 16) | 0, e[7] = l[7] + (l[6] << 8 | l[6] >>> 24) + l[5] | 0
            }

            var n = e, a = n.lib, i = a.StreamCipher, o = n.algo, r = [], s = [], l = [], d = o.Rabbit = i.extend({
                _doReset: function () {
                    for (var e = this._key.words, n = this.cfg.iv, a = 0; a < 4; a++)e[a] = 16711935 & (e[a] << 8 | e[a] >>> 24) | 4278255360 & (e[a] << 24 | e[a] >>> 8);
                    var i = this._X = [e[0], e[3] << 16 | e[2] >>> 16, e[1], e[0] << 16 | e[3] >>> 16, e[2], e[1] << 16 | e[0] >>> 16, e[3], e[2] << 16 | e[1] >>> 16], o = this._C = [e[2] << 16 | e[2] >>> 16, 4294901760 & e[0] | 65535 & e[1], e[3] << 16 | e[3] >>> 16, 4294901760 & e[1] | 65535 & e[2], e[0] << 16 | e[0] >>> 16, 4294901760 & e[2] | 65535 & e[3], e[1] << 16 | e[1] >>> 16, 4294901760 & e[3] | 65535 & e[0]];
                    this._b = 0;
                    for (var a = 0; a < 4; a++)t.call(this);
                    for (var a = 0; a < 8; a++)o[a] ^= i[a + 4 & 7];
                    if (n) {
                        var r = n.words, s = r[0], l = r[1], d = 16711935 & (s << 8 | s >>> 24) | 4278255360 & (s << 24 | s >>> 8), c = 16711935 & (l << 8 | l >>> 24) | 4278255360 & (l << 24 | l >>> 8), u = d >>> 16 | 4294901760 & c, m = c << 16 | 65535 & d;
                        o[0] ^= d, o[1] ^= u, o[2] ^= c, o[3] ^= m, o[4] ^= d, o[5] ^= u, o[6] ^= c, o[7] ^= m;
                        for (var a = 0; a < 4; a++)t.call(this)
                    }
                }, _doProcessBlock: function (e, n) {
                    var a = this._X;
                    t.call(this), r[0] = a[0] ^ a[5] >>> 16 ^ a[3] << 16, r[1] = a[2] ^ a[7] >>> 16 ^ a[5] << 16, r[2] = a[4] ^ a[1] >>> 16 ^ a[7] << 16, r[3] = a[6] ^ a[3] >>> 16 ^ a[1] << 16;
                    for (var i = 0; i < 4; i++)r[i] = 16711935 & (r[i] << 8 | r[i] >>> 24) | 4278255360 & (r[i] << 24 | r[i] >>> 8), e[n + i] ^= r[i]
                }, blockSize: 4, ivSize: 2
            });
            n.Rabbit = i._createHelper(d)
        }(), e.Rabbit
    })
}, function (e, t, n) {
    !function (a, i, o) {
        e.exports = t = i(n(18), n(22), n(23), n(33), n(34))
    }(this, function (e) {
        return function () {
            function t() {
                for (var e = this._X, t = this._C, n = 0; n < 8; n++)s[n] = t[n];
                t[0] = t[0] + 1295307597 + this._b | 0, t[1] = t[1] + 3545052371 + (t[0] >>> 0 < s[0] >>> 0 ? 1 : 0) | 0, t[2] = t[2] + 886263092 + (t[1] >>> 0 < s[1] >>> 0 ? 1 : 0) | 0, t[3] = t[3] + 1295307597 + (t[2] >>> 0 < s[2] >>> 0 ? 1 : 0) | 0, t[4] = t[4] + 3545052371 + (t[3] >>> 0 < s[3] >>> 0 ? 1 : 0) | 0, t[5] = t[5] + 886263092 + (t[4] >>> 0 < s[4] >>> 0 ? 1 : 0) | 0, t[6] = t[6] + 1295307597 + (t[5] >>> 0 < s[5] >>> 0 ? 1 : 0) | 0, t[7] = t[7] + 3545052371 + (t[6] >>> 0 < s[6] >>> 0 ? 1 : 0) | 0, this._b = t[7] >>> 0 < s[7] >>> 0 ? 1 : 0;
                for (var n = 0; n < 8; n++) {
                    var a = e[n] + t[n], i = 65535 & a, o = a >>> 16, r = ((i * i >>> 17) + i * o >>> 15) + o * o, d = ((4294901760 & a) * a | 0) + ((65535 & a) * a | 0);
                    l[n] = r ^ d
                }
                e[0] = l[0] + (l[7] << 16 | l[7] >>> 16) + (l[6] << 16 | l[6] >>> 16) | 0, e[1] = l[1] + (l[0] << 8 | l[0] >>> 24) + l[7] | 0, e[2] = l[2] + (l[1] << 16 | l[1] >>> 16) + (l[0] << 16 | l[0] >>> 16) | 0, e[3] = l[3] + (l[2] << 8 | l[2] >>> 24) + l[1] | 0, e[4] = l[4] + (l[3] << 16 | l[3] >>> 16) + (l[2] << 16 | l[2] >>> 16) | 0, e[5] = l[5] + (l[4] << 8 | l[4] >>> 24) + l[3] | 0, e[6] = l[6] + (l[5] << 16 | l[5] >>> 16) + (l[4] << 16 | l[4] >>> 16) | 0, e[7] = l[7] + (l[6] << 8 | l[6] >>> 24) + l[5] | 0
            }

            var n = e, a = n.lib, i = a.StreamCipher, o = n.algo, r = [], s = [], l = [], d = o.RabbitLegacy = i.extend({
                _doReset: function () {
                    var e = this._key.words, n = this.cfg.iv, a = this._X = [e[0], e[3] << 16 | e[2] >>> 16, e[1], e[0] << 16 | e[3] >>> 16, e[2], e[1] << 16 | e[0] >>> 16, e[3], e[2] << 16 | e[1] >>> 16], i = this._C = [e[2] << 16 | e[2] >>> 16, 4294901760 & e[0] | 65535 & e[1], e[3] << 16 | e[3] >>> 16, 4294901760 & e[1] | 65535 & e[2], e[0] << 16 | e[0] >>> 16, 4294901760 & e[2] | 65535 & e[3], e[1] << 16 | e[1] >>> 16, 4294901760 & e[3] | 65535 & e[0]];
                    this._b = 0;
                    for (var o = 0; o < 4; o++)t.call(this);
                    for (var o = 0; o < 8; o++)i[o] ^= a[o + 4 & 7];
                    if (n) {
                        var r = n.words, s = r[0], l = r[1], d = 16711935 & (s << 8 | s >>> 24) | 4278255360 & (s << 24 | s >>> 8), c = 16711935 & (l << 8 | l >>> 24) | 4278255360 & (l << 24 | l >>> 8), u = d >>> 16 | 4294901760 & c, m = c << 16 | 65535 & d;
                        i[0] ^= d, i[1] ^= u, i[2] ^= c, i[3] ^= m, i[4] ^= d, i[5] ^= u, i[6] ^= c, i[7] ^= m;
                        for (var o = 0; o < 4; o++)t.call(this)
                    }
                }, _doProcessBlock: function (e, n) {
                    var a = this._X;
                    t.call(this), r[0] = a[0] ^ a[5] >>> 16 ^ a[3] << 16, r[1] = a[2] ^ a[7] >>> 16 ^ a[5] << 16, r[2] = a[4] ^ a[1] >>> 16 ^ a[7] << 16, r[3] = a[6] ^ a[3] >>> 16 ^ a[1] << 16;
                    for (var i = 0; i < 4; i++)r[i] = 16711935 & (r[i] << 8 | r[i] >>> 24) | 4278255360 & (r[i] << 24 | r[i] >>> 8), e[n + i] ^= r[i]
                }, blockSize: 4, ivSize: 2
            });
            n.RabbitLegacy = i._createHelper(d)
        }(), e.RabbitLegacy
    })
}, function (e, t, n) {
    (function (e) {
        !function (t, n) {
            e.exports = n()
        }(this, function () {
            "use strict";
            function t() {
                return ga.apply(null, arguments)
            }

            function a(e) {
                ga = e
            }

            function i(e) {
                return e instanceof Array || "[object Array]" === Object.prototype.toString.call(e)
            }

            function o(e) {
                return null != e && "[object Object]" === Object.prototype.toString.call(e)
            }

            function r(e) {
                var t;
                for (t in e)return !1;
                return !0
            }

            function s(e) {
                return "number" == typeof e || "[object Number]" === Object.prototype.toString.call(e)
            }

            function l(e) {
                return e instanceof Date || "[object Date]" === Object.prototype.toString.call(e)
            }

            function d(e, t) {
                var n, a = [];
                for (n = 0; n < e.length; ++n)a.push(t(e[n], n));
                return a
            }

            function c(e, t) {
                return Object.prototype.hasOwnProperty.call(e, t)
            }

            function u(e, t) {
                for (var n in t)c(t, n) && (e[n] = t[n]);
                return c(t, "toString") && (e.toString = t.toString), c(t, "valueOf") && (e.valueOf = t.valueOf), e
            }

            function m(e, t, n, a) {
                return vt(e, t, n, a, !0).utc()
            }

            function _() {
                return {
                    empty: !1,
                    unusedTokens: [],
                    unusedInput: [],
                    overflow: -2,
                    charsLeftOver: 0,
                    nullInput: !1,
                    invalidMonth: null,
                    invalidFormat: !1,
                    userInvalidated: !1,
                    iso: !1,
                    parsedDateParts: [],
                    meridiem: null
                }
            }

            function p(e) {
                return null == e._pf && (e._pf = _()), e._pf
            }

            function h(e) {
                if (null == e._isValid) {
                    var t = p(e), n = va.call(t.parsedDateParts, function (e) {
                        return null != e
                    }), a = !isNaN(e._d.getTime()) && t.overflow < 0 && !t.empty && !t.invalidMonth && !t.invalidWeekday && !t.nullInput && !t.invalidFormat && !t.userInvalidated && (!t.meridiem || t.meridiem && n);
                    if (e._strict && (a = a && 0 === t.charsLeftOver && 0 === t.unusedTokens.length && void 0 === t.bigHour), null != Object.isFrozen && Object.isFrozen(e))return a;
                    e._isValid = a
                }
                return e._isValid
            }

            function f(e) {
                var t = m(NaN);
                return null != e ? u(p(t), e) : p(t).userInvalidated = !0, t
            }

            function g(e) {
                return void 0 === e
            }

            function y(e, t) {
                var n, a, i;
                if (g(t._isAMomentObject) || (e._isAMomentObject = t._isAMomentObject), g(t._i) || (e._i = t._i), g(t._f) || (e._f = t._f), g(t._l) || (e._l = t._l), g(t._strict) || (e._strict = t._strict), g(t._tzm) || (e._tzm = t._tzm), g(t._isUTC) || (e._isUTC = t._isUTC), g(t._offset) || (e._offset = t._offset), g(t._pf) || (e._pf = p(t)), g(t._locale) || (e._locale = t._locale), ba.length > 0)for (n in ba)a = ba[n], i = t[a], g(i) || (e[a] = i);
                return e
            }

            function v(e) {
                y(this, e), this._d = new Date(null != e._d ? e._d.getTime() : NaN), this.isValid() || (this._d = new Date(NaN)), wa === !1 && (wa = !0, t.updateOffset(this), wa = !1)
            }

            function b(e) {
                return e instanceof v || null != e && null != e._isAMomentObject
            }

            function w(e) {
                return e < 0 ? Math.ceil(e) || 0 : Math.floor(e)
            }

            function M(e) {
                var t = +e, n = 0;
                return 0 !== t && isFinite(t) && (n = w(t)), n
            }

            function k(e, t, n) {
                var a, i = Math.min(e.length, t.length), o = Math.abs(e.length - t.length), r = 0;
                for (a = 0; a < i; a++)(n && e[a] !== t[a] || !n && M(e[a]) !== M(t[a])) && r++;
                return r + o
            }

            function L(e) {
                t.suppressDeprecationWarnings === !1 && "undefined" != typeof console && console.warn && console.warn("Deprecation warning: " + e)
            }

            function x(e, n) {
                var a = !0;
                return u(function () {
                    if (null != t.deprecationHandler && t.deprecationHandler(null, e), a) {
                        for (var i, o = [], r = 0; r < arguments.length; r++) {
                            if (i = "", "object" == typeof arguments[r]) {
                                i += "\n[" + r + "] ";
                                for (var s in arguments[0])i += s + ": " + arguments[0][s] + ", ";
                                i = i.slice(0, -2)
                            } else i = arguments[r];
                            o.push(i)
                        }
                        L(e + "\nArguments: " + Array.prototype.slice.call(o).join("") + "\n" + (new Error).stack), a = !1
                    }
                    return n.apply(this, arguments)
                }, n)
            }

            function Y(e, n) {
                null != t.deprecationHandler && t.deprecationHandler(e, n), Ma[e] || (L(n), Ma[e] = !0)
            }

            function D(e) {
                return e instanceof Function || "[object Function]" === Object.prototype.toString.call(e)
            }

            function T(e) {
                var t, n;
                for (n in e)t = e[n], D(t) ? this[n] = t : this["_" + n] = t;
                this._config = e, this._ordinalParseLenient = new RegExp(this._ordinalParse.source + "|" + /\d{1,2}/.source)
            }

            function S(e, t) {
                var n, a = u({}, e);
                for (n in t)c(t, n) && (o(e[n]) && o(t[n]) ? (a[n] = {}, u(a[n], e[n]), u(a[n], t[n])) : null != t[n] ? a[n] = t[n] : delete a[n]);
                for (n in e)c(e, n) && !c(t, n) && o(e[n]) && (a[n] = u({}, a[n]));
                return a
            }

            function z(e) {
                null != e && this.set(e)
            }

            function H(e, t, n) {
                var a = this._calendar[e] || this._calendar.sameElse;
                return D(a) ? a.call(t, n) : a
            }

            function j(e) {
                var t = this._longDateFormat[e], n = this._longDateFormat[e.toUpperCase()];
                return t || !n ? t : (this._longDateFormat[e] = n.replace(/MMMM|MM|DD|dddd/g, function (e) {
                    return e.slice(1)
                }), this._longDateFormat[e])
            }

            function F() {
                return this._invalidDate
            }

            function P(e) {
                return this._ordinal.replace("%d", e)
            }

            function C(e, t, n, a) {
                var i = this._relativeTime[n];
                return D(i) ? i(e, t, n, a) : i.replace(/%d/i, e)
            }

            function E(e, t) {
                var n = this._relativeTime[e > 0 ? "future" : "past"];
                return D(n) ? n(t) : n.replace(/%s/i, t)
            }

            function A(e, t) {
                var n = e.toLowerCase();
                ja[n] = ja[n + "s"] = ja[t] = e
            }

            function B(e) {
                return "string" == typeof e ? ja[e] || ja[e.toLowerCase()] : void 0
            }

            function I(e) {
                var t, n, a = {};
                for (n in e)c(e, n) && (t = B(n), t && (a[t] = e[n]));
                return a
            }

            function W(e, t) {
                Fa[e] = t
            }

            function O(e) {
                var t = [];
                for (var n in e)t.push({unit: n, priority: Fa[n]});
                return t.sort(function (e, t) {
                    return e.priority - t.priority
                }), t
            }

            function R(e, n) {
                return function (a) {
                    return null != a ? (V(this, e, a), t.updateOffset(this, n), this) : N(this, e)
                }
            }

            function N(e, t) {
                return e.isValid() ? e._d["get" + (e._isUTC ? "UTC" : "") + t]() : NaN
            }

            function V(e, t, n) {
                e.isValid() && e._d["set" + (e._isUTC ? "UTC" : "") + t](n)
            }

            function J(e) {
                return e = B(e), D(this[e]) ? this[e]() : this
            }

            function U(e, t) {
                if ("object" == typeof e) {
                    e = I(e);
                    for (var n = O(e), a = 0; a < n.length; a++)this[n[a].unit](e[n[a].unit])
                } else if (e = B(e), D(this[e]))return this[e](t);
                return this
            }

            function q(e, t, n) {
                var a = "" + Math.abs(e), i = t - a.length, o = e >= 0;
                return (o ? n ? "+" : "" : "-") + Math.pow(10, Math.max(0, i)).toString().substr(1) + a
            }

            function G(e, t, n, a) {
                var i = a;
                "string" == typeof a && (i = function () {
                    return this[a]()
                }), e && (Aa[e] = i), t && (Aa[t[0]] = function () {
                    return q(i.apply(this, arguments), t[1], t[2])
                }), n && (Aa[n] = function () {
                    return this.localeData().ordinal(i.apply(this, arguments), e)
                })
            }

            function $(e) {
                return e.match(/\[[\s\S]/) ? e.replace(/^\[|\]$/g, "") : e.replace(/\\/g, "")
            }

            function K(e) {
                var t, n, a = e.match(Pa);
                for (t = 0, n = a.length; t < n; t++)Aa[a[t]] ? a[t] = Aa[a[t]] : a[t] = $(a[t]);
                return function (t) {
                    var i, o = "";
                    for (i = 0; i < n; i++)o += a[i] instanceof Function ? a[i].call(t, e) : a[i];
                    return o
                }
            }

            function X(e, t) {
                return e.isValid() ? (t = Z(t, e.localeData()), Ea[t] = Ea[t] || K(t), Ea[t](e)) : e.localeData().invalidDate()
            }

            function Z(e, t) {
                function n(e) {
                    return t.longDateFormat(e) || e
                }

                var a = 5;
                for (Ca.lastIndex = 0; a >= 0 && Ca.test(e);)e = e.replace(Ca, n), Ca.lastIndex = 0, a -= 1;
                return e
            }

            function Q(e, t, n) {
                ti[e] = D(t) ? t : function (e, a) {
                    return e && n ? n : t
                }
            }

            function ee(e, t) {
                return c(ti, e) ? ti[e](t._strict, t._locale) : new RegExp(te(e))
            }

            function te(e) {
                return ne(e.replace("\\", "").replace(/\\(\[)|\\(\])|\[([^\]\[]*)\]|\\(.)/g, function (e, t, n, a, i) {
                    return t || n || a || i
                }))
            }

            function ne(e) {
                return e.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&")
            }

            function ae(e, t) {
                var n, a = t;
                for ("string" == typeof e && (e = [e]), s(t) && (a = function (e, n) {
                    n[t] = M(e)
                }), n = 0; n < e.length; n++)ni[e[n]] = a
            }

            function ie(e, t) {
                ae(e, function (e, n, a, i) {
                    a._w = a._w || {}, t(e, a._w, a, i)
                })
            }

            function oe(e, t, n) {
                null != t && c(ni, e) && ni[e](t, n._a, n, e)
            }

            function re(e, t) {
                return new Date(Date.UTC(e, t + 1, 0)).getUTCDate()
            }

            function se(e, t) {
                return e ? i(this._months) ? this._months[e.month()] : this._months[(this._months.isFormat || _i).test(t) ? "format" : "standalone"][e.month()] : this._months
            }

            function le(e, t) {
                return e ? i(this._monthsShort) ? this._monthsShort[e.month()] : this._monthsShort[_i.test(t) ? "format" : "standalone"][e.month()] : this._monthsShort
            }

            function de(e, t, n) {
                var a, i, o, r = e.toLocaleLowerCase();
                if (!this._monthsParse)for (this._monthsParse = [], this._longMonthsParse = [], this._shortMonthsParse = [], a = 0; a < 12; ++a)o = m([2e3, a]), this._shortMonthsParse[a] = this.monthsShort(o, "").toLocaleLowerCase(), this._longMonthsParse[a] = this.months(o, "").toLocaleLowerCase();
                return n ? "MMM" === t ? (i = mi.call(this._shortMonthsParse, r), i !== -1 ? i : null) : (i = mi.call(this._longMonthsParse, r), i !== -1 ? i : null) : "MMM" === t ? (i = mi.call(this._shortMonthsParse, r), i !== -1 ? i : (i = mi.call(this._longMonthsParse, r), i !== -1 ? i : null)) : (i = mi.call(this._longMonthsParse, r), i !== -1 ? i : (i = mi.call(this._shortMonthsParse, r), i !== -1 ? i : null))
            }

            function ce(e, t, n) {
                var a, i, o;
                if (this._monthsParseExact)return de.call(this, e, t, n);
                for (this._monthsParse || (this._monthsParse = [], this._longMonthsParse = [], this._shortMonthsParse = []), a = 0; a < 12; a++) {
                    if (i = m([2e3, a]), n && !this._longMonthsParse[a] && (this._longMonthsParse[a] = new RegExp("^" + this.months(i, "").replace(".", "") + "$", "i"), this._shortMonthsParse[a] = new RegExp("^" + this.monthsShort(i, "").replace(".", "") + "$", "i")), n || this._monthsParse[a] || (o = "^" + this.months(i, "") + "|^" + this.monthsShort(i, ""), this._monthsParse[a] = new RegExp(o.replace(".", ""), "i")), n && "MMMM" === t && this._longMonthsParse[a].test(e))return a;
                    if (n && "MMM" === t && this._shortMonthsParse[a].test(e))return a;
                    if (!n && this._monthsParse[a].test(e))return a
                }
            }

            function ue(e, t) {
                var n;
                if (!e.isValid())return e;
                if ("string" == typeof t)if (/^\d+$/.test(t))t = M(t); else if (t = e.localeData().monthsParse(t), !s(t))return e;
                return n = Math.min(e.date(), re(e.year(), t)), e._d["set" + (e._isUTC ? "UTC" : "") + "Month"](t, n), e
            }

            function me(e) {
                return null != e ? (ue(this, e), t.updateOffset(this, !0), this) : N(this, "Month")
            }

            function _e() {
                return re(this.year(), this.month())
            }

            function pe(e) {
                return this._monthsParseExact ? (c(this, "_monthsRegex") || fe.call(this), e ? this._monthsShortStrictRegex : this._monthsShortRegex) : (c(this, "_monthsShortRegex") || (this._monthsShortRegex = fi), this._monthsShortStrictRegex && e ? this._monthsShortStrictRegex : this._monthsShortRegex)
            }

            function he(e) {
                return this._monthsParseExact ? (c(this, "_monthsRegex") || fe.call(this), e ? this._monthsStrictRegex : this._monthsRegex) : (c(this, "_monthsRegex") || (this._monthsRegex = gi), this._monthsStrictRegex && e ? this._monthsStrictRegex : this._monthsRegex)
            }

            function fe() {
                function e(e, t) {
                    return t.length - e.length
                }

                var t, n, a = [], i = [], o = [];
                for (t = 0; t < 12; t++)n = m([2e3, t]), a.push(this.monthsShort(n, "")), i.push(this.months(n, "")), o.push(this.months(n, "")), o.push(this.monthsShort(n, ""));
                for (a.sort(e), i.sort(e), o.sort(e), t = 0; t < 12; t++)a[t] = ne(a[t]), i[t] = ne(i[t]);
                for (t = 0; t < 24; t++)o[t] = ne(o[t]);
                this._monthsRegex = new RegExp("^(" + o.join("|") + ")", "i"), this._monthsShortRegex = this._monthsRegex, this._monthsStrictRegex = new RegExp("^(" + i.join("|") + ")", "i"), this._monthsShortStrictRegex = new RegExp("^(" + a.join("|") + ")", "i")
            }

            function ge(e) {
                return ye(e) ? 366 : 365
            }

            function ye(e) {
                return e % 4 === 0 && e % 100 !== 0 || e % 400 === 0
            }

            function ve() {
                return ye(this.year())
            }

            function be(e, t, n, a, i, o, r) {
                var s = new Date(e, t, n, a, i, o, r);
                return e < 100 && e >= 0 && isFinite(s.getFullYear()) && s.setFullYear(e), s
            }

            function we(e) {
                var t = new Date(Date.UTC.apply(null, arguments));
                return e < 100 && e >= 0 && isFinite(t.getUTCFullYear()) && t.setUTCFullYear(e), t
            }

            function Me(e, t, n) {
                var a = 7 + t - n, i = (7 + we(e, 0, a).getUTCDay() - t) % 7;
                return -i + a - 1
            }

            function ke(e, t, n, a, i) {
                var o, r, s = (7 + n - a) % 7, l = Me(e, a, i), d = 1 + 7 * (t - 1) + s + l;
                return d <= 0 ? (o = e - 1, r = ge(o) + d) : d > ge(e) ? (o = e + 1, r = d - ge(e)) : (o = e, r = d), {
                    year: o,
                    dayOfYear: r
                }
            }

            function Le(e, t, n) {
                var a, i, o = Me(e.year(), t, n), r = Math.floor((e.dayOfYear() - o - 1) / 7) + 1;
                return r < 1 ? (i = e.year() - 1, a = r + xe(i, t, n)) : r > xe(e.year(), t, n) ? (a = r - xe(e.year(), t, n), i = e.year() + 1) : (i = e.year(), a = r), {
                    week: a,
                    year: i
                }
            }

            function xe(e, t, n) {
                var a = Me(e, t, n), i = Me(e + 1, t, n);
                return (ge(e) - a + i) / 7
            }

            function Ye(e) {
                return Le(e, this._week.dow, this._week.doy).week
            }

            function De() {
                return this._week.dow
            }

            function Te() {
                return this._week.doy
            }

            function Se(e) {
                var t = this.localeData().week(this);
                return null == e ? t : this.add(7 * (e - t), "d")
            }

            function ze(e) {
                var t = Le(this, 1, 4).week;
                return null == e ? t : this.add(7 * (e - t), "d")
            }

            function He(e, t) {
                return "string" != typeof e ? e : isNaN(e) ? (e = t.weekdaysParse(e), "number" == typeof e ? e : null) : parseInt(e, 10)
            }

            function je(e, t) {
                return "string" == typeof e ? t.weekdaysParse(e) % 7 || 7 : isNaN(e) ? null : e
            }

            function Fe(e, t) {
                return e ? i(this._weekdays) ? this._weekdays[e.day()] : this._weekdays[this._weekdays.isFormat.test(t) ? "format" : "standalone"][e.day()] : this._weekdays;
            }

            function Pe(e) {
                return e ? this._weekdaysShort[e.day()] : this._weekdaysShort
            }

            function Ce(e) {
                return e ? this._weekdaysMin[e.day()] : this._weekdaysMin
            }

            function Ee(e, t, n) {
                var a, i, o, r = e.toLocaleLowerCase();
                if (!this._weekdaysParse)for (this._weekdaysParse = [], this._shortWeekdaysParse = [], this._minWeekdaysParse = [], a = 0; a < 7; ++a)o = m([2e3, 1]).day(a), this._minWeekdaysParse[a] = this.weekdaysMin(o, "").toLocaleLowerCase(), this._shortWeekdaysParse[a] = this.weekdaysShort(o, "").toLocaleLowerCase(), this._weekdaysParse[a] = this.weekdays(o, "").toLocaleLowerCase();
                return n ? "dddd" === t ? (i = mi.call(this._weekdaysParse, r), i !== -1 ? i : null) : "ddd" === t ? (i = mi.call(this._shortWeekdaysParse, r), i !== -1 ? i : null) : (i = mi.call(this._minWeekdaysParse, r), i !== -1 ? i : null) : "dddd" === t ? (i = mi.call(this._weekdaysParse, r), i !== -1 ? i : (i = mi.call(this._shortWeekdaysParse, r), i !== -1 ? i : (i = mi.call(this._minWeekdaysParse, r), i !== -1 ? i : null))) : "ddd" === t ? (i = mi.call(this._shortWeekdaysParse, r), i !== -1 ? i : (i = mi.call(this._weekdaysParse, r), i !== -1 ? i : (i = mi.call(this._minWeekdaysParse, r), i !== -1 ? i : null))) : (i = mi.call(this._minWeekdaysParse, r), i !== -1 ? i : (i = mi.call(this._weekdaysParse, r), i !== -1 ? i : (i = mi.call(this._shortWeekdaysParse, r), i !== -1 ? i : null)))
            }

            function Ae(e, t, n) {
                var a, i, o;
                if (this._weekdaysParseExact)return Ee.call(this, e, t, n);
                for (this._weekdaysParse || (this._weekdaysParse = [], this._minWeekdaysParse = [], this._shortWeekdaysParse = [], this._fullWeekdaysParse = []), a = 0; a < 7; a++) {
                    if (i = m([2e3, 1]).day(a), n && !this._fullWeekdaysParse[a] && (this._fullWeekdaysParse[a] = new RegExp("^" + this.weekdays(i, "").replace(".", ".?") + "$", "i"), this._shortWeekdaysParse[a] = new RegExp("^" + this.weekdaysShort(i, "").replace(".", ".?") + "$", "i"), this._minWeekdaysParse[a] = new RegExp("^" + this.weekdaysMin(i, "").replace(".", ".?") + "$", "i")), this._weekdaysParse[a] || (o = "^" + this.weekdays(i, "") + "|^" + this.weekdaysShort(i, "") + "|^" + this.weekdaysMin(i, ""), this._weekdaysParse[a] = new RegExp(o.replace(".", ""), "i")), n && "dddd" === t && this._fullWeekdaysParse[a].test(e))return a;
                    if (n && "ddd" === t && this._shortWeekdaysParse[a].test(e))return a;
                    if (n && "dd" === t && this._minWeekdaysParse[a].test(e))return a;
                    if (!n && this._weekdaysParse[a].test(e))return a
                }
            }

            function Be(e) {
                if (!this.isValid())return null != e ? this : NaN;
                var t = this._isUTC ? this._d.getUTCDay() : this._d.getDay();
                return null != e ? (e = He(e, this.localeData()), this.add(e - t, "d")) : t
            }

            function Ie(e) {
                if (!this.isValid())return null != e ? this : NaN;
                var t = (this.day() + 7 - this.localeData()._week.dow) % 7;
                return null == e ? t : this.add(e - t, "d")
            }

            function We(e) {
                if (!this.isValid())return null != e ? this : NaN;
                if (null != e) {
                    var t = je(e, this.localeData());
                    return this.day(this.day() % 7 ? t : t - 7)
                }
                return this.day() || 7
            }

            function Oe(e) {
                return this._weekdaysParseExact ? (c(this, "_weekdaysRegex") || Ve.call(this), e ? this._weekdaysStrictRegex : this._weekdaysRegex) : (c(this, "_weekdaysRegex") || (this._weekdaysRegex = ki), this._weekdaysStrictRegex && e ? this._weekdaysStrictRegex : this._weekdaysRegex)
            }

            function Re(e) {
                return this._weekdaysParseExact ? (c(this, "_weekdaysRegex") || Ve.call(this), e ? this._weekdaysShortStrictRegex : this._weekdaysShortRegex) : (c(this, "_weekdaysShortRegex") || (this._weekdaysShortRegex = Li), this._weekdaysShortStrictRegex && e ? this._weekdaysShortStrictRegex : this._weekdaysShortRegex)
            }

            function Ne(e) {
                return this._weekdaysParseExact ? (c(this, "_weekdaysRegex") || Ve.call(this), e ? this._weekdaysMinStrictRegex : this._weekdaysMinRegex) : (c(this, "_weekdaysMinRegex") || (this._weekdaysMinRegex = xi), this._weekdaysMinStrictRegex && e ? this._weekdaysMinStrictRegex : this._weekdaysMinRegex)
            }

            function Ve() {
                function e(e, t) {
                    return t.length - e.length
                }

                var t, n, a, i, o, r = [], s = [], l = [], d = [];
                for (t = 0; t < 7; t++)n = m([2e3, 1]).day(t), a = this.weekdaysMin(n, ""), i = this.weekdaysShort(n, ""), o = this.weekdays(n, ""), r.push(a), s.push(i), l.push(o), d.push(a), d.push(i), d.push(o);
                for (r.sort(e), s.sort(e), l.sort(e), d.sort(e), t = 0; t < 7; t++)s[t] = ne(s[t]), l[t] = ne(l[t]), d[t] = ne(d[t]);
                this._weekdaysRegex = new RegExp("^(" + d.join("|") + ")", "i"), this._weekdaysShortRegex = this._weekdaysRegex, this._weekdaysMinRegex = this._weekdaysRegex, this._weekdaysStrictRegex = new RegExp("^(" + l.join("|") + ")", "i"), this._weekdaysShortStrictRegex = new RegExp("^(" + s.join("|") + ")", "i"), this._weekdaysMinStrictRegex = new RegExp("^(" + r.join("|") + ")", "i")
            }

            function Je() {
                return this.hours() % 12 || 12
            }

            function Ue() {
                return this.hours() || 24
            }

            function qe(e, t) {
                G(e, 0, 0, function () {
                    return this.localeData().meridiem(this.hours(), this.minutes(), t)
                })
            }

            function Ge(e, t) {
                return t._meridiemParse
            }

            function $e(e) {
                return "p" === (e + "").toLowerCase().charAt(0)
            }

            function Ke(e, t, n) {
                return e > 11 ? n ? "pm" : "PM" : n ? "am" : "AM"
            }

            function Xe(e) {
                return e ? e.toLowerCase().replace("_", "-") : e
            }

            function Ze(e) {
                for (var t, n, a, i, o = 0; o < e.length;) {
                    for (i = Xe(e[o]).split("-"), t = i.length, n = Xe(e[o + 1]), n = n ? n.split("-") : null; t > 0;) {
                        if (a = Qe(i.slice(0, t).join("-")))return a;
                        if (n && n.length >= t && k(i, n, !0) >= t - 1)break;
                        t--
                    }
                    o++
                }
                return null
            }

            function Qe(t) {
                var a = null;
                if (!zi[t] && "undefined" != typeof e && e && e.exports)try {
                    a = Yi._abbr, n(53)("./" + t), et(a)
                } catch (i) {
                }
                return zi[t]
            }

            function et(e, t) {
                var n;
                return e && (n = g(t) ? at(e) : tt(e, t), n && (Yi = n)), Yi._abbr
            }

            function tt(e, t) {
                if (null !== t) {
                    var n = Si;
                    if (t.abbr = e, null != zi[e])Y("defineLocaleOverride", "use moment.updateLocale(localeName, config) to change an existing locale. moment.defineLocale(localeName, config) should only be used for creating a new locale See http://momentjs.com/guides/#/warnings/define-locale/ for more info."), n = zi[e]._config; else if (null != t.parentLocale) {
                        if (null == zi[t.parentLocale])return Hi[t.parentLocale] || (Hi[t.parentLocale] = []), Hi[t.parentLocale].push({
                            name: e,
                            config: t
                        }), null;
                        n = zi[t.parentLocale]._config
                    }
                    return zi[e] = new z(S(n, t)), Hi[e] && Hi[e].forEach(function (e) {
                        tt(e.name, e.config)
                    }), et(e), zi[e]
                }
                return delete zi[e], null
            }

            function nt(e, t) {
                if (null != t) {
                    var n, a = Si;
                    null != zi[e] && (a = zi[e]._config), t = S(a, t), n = new z(t), n.parentLocale = zi[e], zi[e] = n, et(e)
                } else null != zi[e] && (null != zi[e].parentLocale ? zi[e] = zi[e].parentLocale : null != zi[e] && delete zi[e]);
                return zi[e]
            }

            function at(e) {
                var t;
                if (e && e._locale && e._locale._abbr && (e = e._locale._abbr), !e)return Yi;
                if (!i(e)) {
                    if (t = Qe(e))return t;
                    e = [e]
                }
                return Ze(e)
            }

            function it() {
                return xa(zi)
            }

            function ot(e) {
                var t, n = e._a;
                return n && p(e).overflow === -2 && (t = n[ii] < 0 || n[ii] > 11 ? ii : n[oi] < 1 || n[oi] > re(n[ai], n[ii]) ? oi : n[ri] < 0 || n[ri] > 24 || 24 === n[ri] && (0 !== n[si] || 0 !== n[li] || 0 !== n[di]) ? ri : n[si] < 0 || n[si] > 59 ? si : n[li] < 0 || n[li] > 59 ? li : n[di] < 0 || n[di] > 999 ? di : -1, p(e)._overflowDayOfYear && (t < ai || t > oi) && (t = oi), p(e)._overflowWeeks && t === -1 && (t = ci), p(e)._overflowWeekday && t === -1 && (t = ui), p(e).overflow = t), e
            }

            function rt(e) {
                var t, n, a, i, o, r, s = e._i, l = ji.exec(s) || Fi.exec(s);
                if (l) {
                    for (p(e).iso = !0, t = 0, n = Ci.length; t < n; t++)if (Ci[t][1].exec(l[1])) {
                        i = Ci[t][0], a = Ci[t][2] !== !1;
                        break
                    }
                    if (null == i)return void(e._isValid = !1);
                    if (l[3]) {
                        for (t = 0, n = Ei.length; t < n; t++)if (Ei[t][1].exec(l[3])) {
                            o = (l[2] || " ") + Ei[t][0];
                            break
                        }
                        if (null == o)return void(e._isValid = !1)
                    }
                    if (!a && null != o)return void(e._isValid = !1);
                    if (l[4]) {
                        if (!Pi.exec(l[4]))return void(e._isValid = !1);
                        r = "Z"
                    }
                    e._f = i + (o || "") + (r || ""), mt(e)
                } else e._isValid = !1
            }

            function st(e) {
                var n = Ai.exec(e._i);
                return null !== n ? void(e._d = new Date((+n[1]))) : (rt(e), void(e._isValid === !1 && (delete e._isValid, t.createFromInputFallback(e))))
            }

            function lt(e, t, n) {
                return null != e ? e : null != t ? t : n
            }

            function dt(e) {
                var n = new Date(t.now());
                return e._useUTC ? [n.getUTCFullYear(), n.getUTCMonth(), n.getUTCDate()] : [n.getFullYear(), n.getMonth(), n.getDate()]
            }

            function ct(e) {
                var t, n, a, i, o = [];
                if (!e._d) {
                    for (a = dt(e), e._w && null == e._a[oi] && null == e._a[ii] && ut(e), e._dayOfYear && (i = lt(e._a[ai], a[ai]), e._dayOfYear > ge(i) && (p(e)._overflowDayOfYear = !0), n = we(i, 0, e._dayOfYear), e._a[ii] = n.getUTCMonth(), e._a[oi] = n.getUTCDate()), t = 0; t < 3 && null == e._a[t]; ++t)e._a[t] = o[t] = a[t];
                    for (; t < 7; t++)e._a[t] = o[t] = null == e._a[t] ? 2 === t ? 1 : 0 : e._a[t];
                    24 === e._a[ri] && 0 === e._a[si] && 0 === e._a[li] && 0 === e._a[di] && (e._nextDay = !0, e._a[ri] = 0), e._d = (e._useUTC ? we : be).apply(null, o), null != e._tzm && e._d.setUTCMinutes(e._d.getUTCMinutes() - e._tzm), e._nextDay && (e._a[ri] = 24)
                }
            }

            function ut(e) {
                var t, n, a, i, o, r, s, l;
                if (t = e._w, null != t.GG || null != t.W || null != t.E)o = 1, r = 4, n = lt(t.GG, e._a[ai], Le(bt(), 1, 4).year), a = lt(t.W, 1), i = lt(t.E, 1), (i < 1 || i > 7) && (l = !0); else {
                    o = e._locale._week.dow, r = e._locale._week.doy;
                    var d = Le(bt(), o, r);
                    n = lt(t.gg, e._a[ai], d.year), a = lt(t.w, d.week), null != t.d ? (i = t.d, (i < 0 || i > 6) && (l = !0)) : null != t.e ? (i = t.e + o, (t.e < 0 || t.e > 6) && (l = !0)) : i = o
                }
                a < 1 || a > xe(n, o, r) ? p(e)._overflowWeeks = !0 : null != l ? p(e)._overflowWeekday = !0 : (s = ke(n, a, i, o, r), e._a[ai] = s.year, e._dayOfYear = s.dayOfYear)
            }

            function mt(e) {
                if (e._f === t.ISO_8601)return void rt(e);
                e._a = [], p(e).empty = !0;
                var n, a, i, o, r, s = "" + e._i, l = s.length, d = 0;
                for (i = Z(e._f, e._locale).match(Pa) || [], n = 0; n < i.length; n++)o = i[n], a = (s.match(ee(o, e)) || [])[0], a && (r = s.substr(0, s.indexOf(a)), r.length > 0 && p(e).unusedInput.push(r), s = s.slice(s.indexOf(a) + a.length), d += a.length), Aa[o] ? (a ? p(e).empty = !1 : p(e).unusedTokens.push(o), oe(o, a, e)) : e._strict && !a && p(e).unusedTokens.push(o);
                p(e).charsLeftOver = l - d, s.length > 0 && p(e).unusedInput.push(s), e._a[ri] <= 12 && p(e).bigHour === !0 && e._a[ri] > 0 && (p(e).bigHour = void 0), p(e).parsedDateParts = e._a.slice(0), p(e).meridiem = e._meridiem, e._a[ri] = _t(e._locale, e._a[ri], e._meridiem), ct(e), ot(e)
            }

            function _t(e, t, n) {
                var a;
                return null == n ? t : null != e.meridiemHour ? e.meridiemHour(t, n) : null != e.isPM ? (a = e.isPM(n), a && t < 12 && (t += 12), a || 12 !== t || (t = 0), t) : t
            }

            function pt(e) {
                var t, n, a, i, o;
                if (0 === e._f.length)return p(e).invalidFormat = !0, void(e._d = new Date(NaN));
                for (i = 0; i < e._f.length; i++)o = 0, t = y({}, e), null != e._useUTC && (t._useUTC = e._useUTC), t._f = e._f[i], mt(t), h(t) && (o += p(t).charsLeftOver, o += 10 * p(t).unusedTokens.length, p(t).score = o, (null == a || o < a) && (a = o, n = t));
                u(e, n || t)
            }

            function ht(e) {
                if (!e._d) {
                    var t = I(e._i);
                    e._a = d([t.year, t.month, t.day || t.date, t.hour, t.minute, t.second, t.millisecond], function (e) {
                        return e && parseInt(e, 10)
                    }), ct(e)
                }
            }

            function ft(e) {
                var t = new v(ot(gt(e)));
                return t._nextDay && (t.add(1, "d"), t._nextDay = void 0), t
            }

            function gt(e) {
                var t = e._i, n = e._f;
                return e._locale = e._locale || at(e._l), null === t || void 0 === n && "" === t ? f({nullInput: !0}) : ("string" == typeof t && (e._i = t = e._locale.preparse(t)), b(t) ? new v(ot(t)) : (l(t) ? e._d = t : i(n) ? pt(e) : n ? mt(e) : yt(e), h(e) || (e._d = null), e))
            }

            function yt(e) {
                var n = e._i;
                void 0 === n ? e._d = new Date(t.now()) : l(n) ? e._d = new Date(n.valueOf()) : "string" == typeof n ? st(e) : i(n) ? (e._a = d(n.slice(0), function (e) {
                    return parseInt(e, 10)
                }), ct(e)) : "object" == typeof n ? ht(e) : s(n) ? e._d = new Date(n) : t.createFromInputFallback(e)
            }

            function vt(e, t, n, a, s) {
                var l = {};
                return n !== !0 && n !== !1 || (a = n, n = void 0), (o(e) && r(e) || i(e) && 0 === e.length) && (e = void 0), l._isAMomentObject = !0, l._useUTC = l._isUTC = s, l._l = n, l._i = e, l._f = t, l._strict = a, ft(l)
            }

            function bt(e, t, n, a) {
                return vt(e, t, n, a, !1)
            }

            function wt(e, t) {
                var n, a;
                if (1 === t.length && i(t[0]) && (t = t[0]), !t.length)return bt();
                for (n = t[0], a = 1; a < t.length; ++a)t[a].isValid() && !t[a][e](n) || (n = t[a]);
                return n
            }

            function Mt() {
                var e = [].slice.call(arguments, 0);
                return wt("isBefore", e)
            }

            function kt() {
                var e = [].slice.call(arguments, 0);
                return wt("isAfter", e)
            }

            function Lt(e) {
                var t = I(e), n = t.year || 0, a = t.quarter || 0, i = t.month || 0, o = t.week || 0, r = t.day || 0, s = t.hour || 0, l = t.minute || 0, d = t.second || 0, c = t.millisecond || 0;
                this._milliseconds = +c + 1e3 * d + 6e4 * l + 1e3 * s * 60 * 60, this._days = +r + 7 * o, this._months = +i + 3 * a + 12 * n, this._data = {}, this._locale = at(), this._bubble()
            }

            function xt(e) {
                return e instanceof Lt
            }

            function Yt(e) {
                return e < 0 ? Math.round(-1 * e) * -1 : Math.round(e)
            }

            function Dt(e, t) {
                G(e, 0, 0, function () {
                    var e = this.utcOffset(), n = "+";
                    return e < 0 && (e = -e, n = "-"), n + q(~~(e / 60), 2) + t + q(~~e % 60, 2)
                })
            }

            function Tt(e, t) {
                var n = (t || "").match(e);
                if (null === n)return null;
                var a = n[n.length - 1] || [], i = (a + "").match(Oi) || ["-", 0, 0], o = +(60 * i[1]) + M(i[2]);
                return 0 === o ? 0 : "+" === i[0] ? o : -o
            }

            function St(e, n) {
                var a, i;
                return n._isUTC ? (a = n.clone(), i = (b(e) || l(e) ? e.valueOf() : bt(e).valueOf()) - a.valueOf(), a._d.setTime(a._d.valueOf() + i), t.updateOffset(a, !1), a) : bt(e).local()
            }

            function zt(e) {
                return 15 * -Math.round(e._d.getTimezoneOffset() / 15)
            }

            function Ht(e, n) {
                var a, i = this._offset || 0;
                if (!this.isValid())return null != e ? this : NaN;
                if (null != e) {
                    if ("string" == typeof e) {
                        if (e = Tt(Za, e), null === e)return this
                    } else Math.abs(e) < 16 && (e = 60 * e);
                    return !this._isUTC && n && (a = zt(this)), this._offset = e, this._isUTC = !0, null != a && this.add(a, "m"), i !== e && (!n || this._changeInProgress ? qt(this, Rt(e - i, "m"), 1, !1) : this._changeInProgress || (this._changeInProgress = !0, t.updateOffset(this, !0), this._changeInProgress = null)), this
                }
                return this._isUTC ? i : zt(this)
            }

            function jt(e, t) {
                return null != e ? ("string" != typeof e && (e = -e), this.utcOffset(e, t), this) : -this.utcOffset()
            }

            function Ft(e) {
                return this.utcOffset(0, e)
            }

            function Pt(e) {
                return this._isUTC && (this.utcOffset(0, e), this._isUTC = !1, e && this.subtract(zt(this), "m")), this
            }

            function Ct() {
                if (null != this._tzm)this.utcOffset(this._tzm); else if ("string" == typeof this._i) {
                    var e = Tt(Xa, this._i);
                    null != e ? this.utcOffset(e) : this.utcOffset(0, !0)
                }
                return this
            }

            function Et(e) {
                return !!this.isValid() && (e = e ? bt(e).utcOffset() : 0, (this.utcOffset() - e) % 60 === 0)
            }

            function At() {
                return this.utcOffset() > this.clone().month(0).utcOffset() || this.utcOffset() > this.clone().month(5).utcOffset()
            }

            function Bt() {
                if (!g(this._isDSTShifted))return this._isDSTShifted;
                var e = {};
                if (y(e, this), e = gt(e), e._a) {
                    var t = e._isUTC ? m(e._a) : bt(e._a);
                    this._isDSTShifted = this.isValid() && k(e._a, t.toArray()) > 0
                } else this._isDSTShifted = !1;
                return this._isDSTShifted
            }

            function It() {
                return !!this.isValid() && !this._isUTC
            }

            function Wt() {
                return !!this.isValid() && this._isUTC
            }

            function Ot() {
                return !!this.isValid() && (this._isUTC && 0 === this._offset)
            }

            function Rt(e, t) {
                var n, a, i, o = e, r = null;
                return xt(e) ? o = {
                    ms: e._milliseconds,
                    d: e._days,
                    M: e._months
                } : s(e) ? (o = {}, t ? o[t] = e : o.milliseconds = e) : (r = Ri.exec(e)) ? (n = "-" === r[1] ? -1 : 1, o = {
                    y: 0,
                    d: M(r[oi]) * n,
                    h: M(r[ri]) * n,
                    m: M(r[si]) * n,
                    s: M(r[li]) * n,
                    ms: M(Yt(1e3 * r[di])) * n
                }) : (r = Ni.exec(e)) ? (n = "-" === r[1] ? -1 : 1, o = {
                    y: Nt(r[2], n),
                    M: Nt(r[3], n),
                    w: Nt(r[4], n),
                    d: Nt(r[5], n),
                    h: Nt(r[6], n),
                    m: Nt(r[7], n),
                    s: Nt(r[8], n)
                }) : null == o ? o = {} : "object" == typeof o && ("from" in o || "to" in o) && (i = Jt(bt(o.from), bt(o.to)), o = {}, o.ms = i.milliseconds, o.M = i.months), a = new Lt(o), xt(e) && c(e, "_locale") && (a._locale = e._locale), a
            }

            function Nt(e, t) {
                var n = e && parseFloat(e.replace(",", "."));
                return (isNaN(n) ? 0 : n) * t
            }

            function Vt(e, t) {
                var n = {milliseconds: 0, months: 0};
                return n.months = t.month() - e.month() + 12 * (t.year() - e.year()), e.clone().add(n.months, "M").isAfter(t) && --n.months, n.milliseconds = +t - +e.clone().add(n.months, "M"), n
            }

            function Jt(e, t) {
                var n;
                return e.isValid() && t.isValid() ? (t = St(t, e), e.isBefore(t) ? n = Vt(e, t) : (n = Vt(t, e), n.milliseconds = -n.milliseconds, n.months = -n.months), n) : {
                    milliseconds: 0,
                    months: 0
                }
            }

            function Ut(e, t) {
                return function (n, a) {
                    var i, o;
                    return null === a || isNaN(+a) || (Y(t, "moment()." + t + "(period, number) is deprecated. Please use moment()." + t + "(number, period). See http://momentjs.com/guides/#/warnings/add-inverted-param/ for more info."), o = n, n = a, a = o), n = "string" == typeof n ? +n : n, i = Rt(n, a), qt(this, i, e), this
                }
            }

            function qt(e, n, a, i) {
                var o = n._milliseconds, r = Yt(n._days), s = Yt(n._months);
                e.isValid() && (i = null == i || i, o && e._d.setTime(e._d.valueOf() + o * a), r && V(e, "Date", N(e, "Date") + r * a), s && ue(e, N(e, "Month") + s * a), i && t.updateOffset(e, r || s))
            }

            function Gt(e, t) {
                var n = e.diff(t, "days", !0);
                return n < -6 ? "sameElse" : n < -1 ? "lastWeek" : n < 0 ? "lastDay" : n < 1 ? "sameDay" : n < 2 ? "nextDay" : n < 7 ? "nextWeek" : "sameElse"
            }

            function $t(e, n) {
                var a = e || bt(), i = St(a, this).startOf("day"), o = t.calendarFormat(this, i) || "sameElse", r = n && (D(n[o]) ? n[o].call(this, a) : n[o]);
                return this.format(r || this.localeData().calendar(o, this, bt(a)))
            }

            function Kt() {
                return new v(this)
            }

            function Xt(e, t) {
                var n = b(e) ? e : bt(e);
                return !(!this.isValid() || !n.isValid()) && (t = B(g(t) ? "millisecond" : t), "millisecond" === t ? this.valueOf() > n.valueOf() : n.valueOf() < this.clone().startOf(t).valueOf())
            }

            function Zt(e, t) {
                var n = b(e) ? e : bt(e);
                return !(!this.isValid() || !n.isValid()) && (t = B(g(t) ? "millisecond" : t), "millisecond" === t ? this.valueOf() < n.valueOf() : this.clone().endOf(t).valueOf() < n.valueOf())
            }

            function Qt(e, t, n, a) {
                return a = a || "()", ("(" === a[0] ? this.isAfter(e, n) : !this.isBefore(e, n)) && (")" === a[1] ? this.isBefore(t, n) : !this.isAfter(t, n))
            }

            function en(e, t) {
                var n, a = b(e) ? e : bt(e);
                return !(!this.isValid() || !a.isValid()) && (t = B(t || "millisecond"), "millisecond" === t ? this.valueOf() === a.valueOf() : (n = a.valueOf(), this.clone().startOf(t).valueOf() <= n && n <= this.clone().endOf(t).valueOf()))
            }

            function tn(e, t) {
                return this.isSame(e, t) || this.isAfter(e, t)
            }

            function nn(e, t) {
                return this.isSame(e, t) || this.isBefore(e, t)
            }

            function an(e, t, n) {
                var a, i, o, r;
                return this.isValid() ? (a = St(e, this), a.isValid() ? (i = 6e4 * (a.utcOffset() - this.utcOffset()), t = B(t), "year" === t || "month" === t || "quarter" === t ? (r = on(this, a), "quarter" === t ? r /= 3 : "year" === t && (r /= 12)) : (o = this - a, r = "second" === t ? o / 1e3 : "minute" === t ? o / 6e4 : "hour" === t ? o / 36e5 : "day" === t ? (o - i) / 864e5 : "week" === t ? (o - i) / 6048e5 : o), n ? r : w(r)) : NaN) : NaN
            }

            function on(e, t) {
                var n, a, i = 12 * (t.year() - e.year()) + (t.month() - e.month()), o = e.clone().add(i, "months");
                return t - o < 0 ? (n = e.clone().add(i - 1, "months"), a = (t - o) / (o - n)) : (n = e.clone().add(i + 1, "months"), a = (t - o) / (n - o)), -(i + a) || 0
            }

            function rn() {
                return this.clone().locale("en").format("ddd MMM DD YYYY HH:mm:ss [GMT]ZZ")
            }

            function sn() {
                var e = this.clone().utc();
                return 0 < e.year() && e.year() <= 9999 ? D(Date.prototype.toISOString) ? this.toDate().toISOString() : X(e, "YYYY-MM-DD[T]HH:mm:ss.SSS[Z]") : X(e, "YYYYYY-MM-DD[T]HH:mm:ss.SSS[Z]")
            }

            function ln() {
                if (!this.isValid())return "moment.invalid(/* " + this._i + " */)";
                var e = "moment", t = "";
                this.isLocal() || (e = 0 === this.utcOffset() ? "moment.utc" : "moment.parseZone", t = "Z");
                var n = "[" + e + '("]', a = 0 < this.year() && this.year() <= 9999 ? "YYYY" : "YYYYYY", i = "-MM-DD[T]HH:mm:ss.SSS", o = t + '[")]';
                return this.format(n + a + i + o)
            }

            function dn(e) {
                e || (e = this.isUtc() ? t.defaultFormatUtc : t.defaultFormat);
                var n = X(this, e);
                return this.localeData().postformat(n)
            }

            function cn(e, t) {
                return this.isValid() && (b(e) && e.isValid() || bt(e).isValid()) ? Rt({
                    to: this,
                    from: e
                }).locale(this.locale()).humanize(!t) : this.localeData().invalidDate()
            }

            function un(e) {
                return this.from(bt(), e)
            }

            function mn(e, t) {
                return this.isValid() && (b(e) && e.isValid() || bt(e).isValid()) ? Rt({
                    from: this,
                    to: e
                }).locale(this.locale()).humanize(!t) : this.localeData().invalidDate()
            }

            function _n(e) {
                return this.to(bt(), e)
            }

            function pn(e) {
                var t;
                return void 0 === e ? this._locale._abbr : (t = at(e), null != t && (this._locale = t), this)
            }

            function hn() {
                return this._locale
            }

            function fn(e) {
                switch (e = B(e)) {
                    case"year":
                        this.month(0);
                    case"quarter":
                    case"month":
                        this.date(1);
                    case"week":
                    case"isoWeek":
                    case"day":
                    case"date":
                        this.hours(0);
                    case"hour":
                        this.minutes(0);
                    case"minute":
                        this.seconds(0);
                    case"second":
                        this.milliseconds(0)
                }
                return "week" === e && this.weekday(0), "isoWeek" === e && this.isoWeekday(1), "quarter" === e && this.month(3 * Math.floor(this.month() / 3)), this
            }

            function gn(e) {
                return e = B(e), void 0 === e || "millisecond" === e ? this : ("date" === e && (e = "day"), this.startOf(e).add(1, "isoWeek" === e ? "week" : e).subtract(1, "ms"))
            }

            function yn() {
                return this._d.valueOf() - 6e4 * (this._offset || 0)
            }

            function vn() {
                return Math.floor(this.valueOf() / 1e3)
            }

            function bn() {
                return new Date(this.valueOf())
            }

            function wn() {
                var e = this;
                return [e.year(), e.month(), e.date(), e.hour(), e.minute(), e.second(), e.millisecond()]
            }

            function Mn() {
                var e = this;
                return {
                    years: e.year(),
                    months: e.month(),
                    date: e.date(),
                    hours: e.hours(),
                    minutes: e.minutes(),
                    seconds: e.seconds(),
                    milliseconds: e.milliseconds()
                }
            }

            function kn() {
                return this.isValid() ? this.toISOString() : null
            }

            function Ln() {
                return h(this)
            }

            function xn() {
                return u({}, p(this))
            }

            function Yn() {
                return p(this).overflow
            }

            function Dn() {
                return {input: this._i, format: this._f, locale: this._locale, isUTC: this._isUTC, strict: this._strict}
            }

            function Tn(e, t) {
                G(0, [e, e.length], 0, t)
            }

            function Sn(e) {
                return Fn.call(this, e, this.week(), this.weekday(), this.localeData()._week.dow, this.localeData()._week.doy)
            }

            function zn(e) {
                return Fn.call(this, e, this.isoWeek(), this.isoWeekday(), 1, 4)
            }

            function Hn() {
                return xe(this.year(), 1, 4)
            }

            function jn() {
                var e = this.localeData()._week;
                return xe(this.year(), e.dow, e.doy)
            }

            function Fn(e, t, n, a, i) {
                var o;
                return null == e ? Le(this, a, i).year : (o = xe(e, a, i), t > o && (t = o), Pn.call(this, e, t, n, a, i))
            }

            function Pn(e, t, n, a, i) {
                var o = ke(e, t, n, a, i), r = we(o.year, 0, o.dayOfYear);
                return this.year(r.getUTCFullYear()), this.month(r.getUTCMonth()), this.date(r.getUTCDate()), this
            }

            function Cn(e) {
                return null == e ? Math.ceil((this.month() + 1) / 3) : this.month(3 * (e - 1) + this.month() % 3)
            }

            function En(e) {
                var t = Math.round((this.clone().startOf("day") - this.clone().startOf("year")) / 864e5) + 1;
                return null == e ? t : this.add(e - t, "d")
            }

            function An(e, t) {
                t[di] = M(1e3 * ("0." + e))
            }

            function Bn() {
                return this._isUTC ? "UTC" : ""
            }

            function In() {
                return this._isUTC ? "Coordinated Universal Time" : ""
            }

            function Wn(e) {
                return bt(1e3 * e)
            }

            function On() {
                return bt.apply(null, arguments).parseZone()
            }

            function Rn(e) {
                return e
            }

            function Nn(e, t, n, a) {
                var i = at(), o = m().set(a, t);
                return i[n](o, e)
            }

            function Vn(e, t, n) {
                if (s(e) && (t = e, e = void 0), e = e || "", null != t)return Nn(e, t, n, "month");
                var a, i = [];
                for (a = 0; a < 12; a++)i[a] = Nn(e, a, n, "month");
                return i
            }

            function Jn(e, t, n, a) {
                "boolean" == typeof e ? (s(t) && (n = t, t = void 0), t = t || "") : (t = e, n = t, e = !1, s(t) && (n = t, t = void 0), t = t || "");
                var i = at(), o = e ? i._week.dow : 0;
                if (null != n)return Nn(t, (n + o) % 7, a, "day");
                var r, l = [];
                for (r = 0; r < 7; r++)l[r] = Nn(t, (r + o) % 7, a, "day");
                return l
            }

            function Un(e, t) {
                return Vn(e, t, "months")
            }

            function qn(e, t) {
                return Vn(e, t, "monthsShort")
            }

            function Gn(e, t, n) {
                return Jn(e, t, n, "weekdays")
            }

            function $n(e, t, n) {
                return Jn(e, t, n, "weekdaysShort")
            }

            function Kn(e, t, n) {
                return Jn(e, t, n, "weekdaysMin")
            }

            function Xn() {
                var e = this._data;
                return this._milliseconds = eo(this._milliseconds), this._days = eo(this._days), this._months = eo(this._months), e.milliseconds = eo(e.milliseconds), e.seconds = eo(e.seconds), e.minutes = eo(e.minutes), e.hours = eo(e.hours), e.months = eo(e.months), e.years = eo(e.years), this
            }

            function Zn(e, t, n, a) {
                var i = Rt(t, n);
                return e._milliseconds += a * i._milliseconds, e._days += a * i._days, e._months += a * i._months, e._bubble()
            }

            function Qn(e, t) {
                return Zn(this, e, t, 1)
            }

            function ea(e, t) {
                return Zn(this, e, t, -1)
            }

            function ta(e) {
                return e < 0 ? Math.floor(e) : Math.ceil(e)
            }

            function na() {
                var e, t, n, a, i, o = this._milliseconds, r = this._days, s = this._months, l = this._data;
                return o >= 0 && r >= 0 && s >= 0 || o <= 0 && r <= 0 && s <= 0 || (o += 864e5 * ta(ia(s) + r), r = 0, s = 0), l.milliseconds = o % 1e3, e = w(o / 1e3), l.seconds = e % 60, t = w(e / 60), l.minutes = t % 60, n = w(t / 60), l.hours = n % 24, r += w(n / 24), i = w(aa(r)), s += i, r -= ta(ia(i)), a = w(s / 12), s %= 12, l.days = r, l.months = s, l.years = a, this
            }

            function aa(e) {
                return 4800 * e / 146097
            }

            function ia(e) {
                return 146097 * e / 4800
            }

            function oa(e) {
                var t, n, a = this._milliseconds;
                if (e = B(e), "month" === e || "year" === e)return t = this._days + a / 864e5, n = this._months + aa(t), "month" === e ? n : n / 12;
                switch (t = this._days + Math.round(ia(this._months)), e) {
                    case"week":
                        return t / 7 + a / 6048e5;
                    case"day":
                        return t + a / 864e5;
                    case"hour":
                        return 24 * t + a / 36e5;
                    case"minute":
                        return 1440 * t + a / 6e4;
                    case"second":
                        return 86400 * t + a / 1e3;
                    case"millisecond":
                        return Math.floor(864e5 * t) + a;
                    default:
                        throw new Error("Unknown unit " + e)
                }
            }

            function ra() {
                return this._milliseconds + 864e5 * this._days + this._months % 12 * 2592e6 + 31536e6 * M(this._months / 12)
            }

            function sa(e) {
                return function () {
                    return this.as(e)
                }
            }

            function la(e) {
                return e = B(e), this[e + "s"]()
            }

            function da(e) {
                return function () {
                    return this._data[e]
                }
            }

            function ca() {
                return w(this.days() / 7)
            }

            function ua(e, t, n, a, i) {
                return i.relativeTime(t || 1, !!n, e, a)
            }

            function ma(e, t, n) {
                var a = Rt(e).abs(), i = go(a.as("s")), o = go(a.as("m")), r = go(a.as("h")), s = go(a.as("d")), l = go(a.as("M")), d = go(a.as("y")), c = i < yo.s && ["s", i] || o <= 1 && ["m"] || o < yo.m && ["mm", o] || r <= 1 && ["h"] || r < yo.h && ["hh", r] || s <= 1 && ["d"] || s < yo.d && ["dd", s] || l <= 1 && ["M"] || l < yo.M && ["MM", l] || d <= 1 && ["y"] || ["yy", d];
                return c[2] = t, c[3] = +e > 0, c[4] = n, ua.apply(null, c)
            }

            function _a(e) {
                return void 0 === e ? go : "function" == typeof e && (go = e, !0)
            }

            function pa(e, t) {
                return void 0 !== yo[e] && (void 0 === t ? yo[e] : (yo[e] = t, !0))
            }

            function ha(e) {
                var t = this.localeData(), n = ma(this, !e, t);
                return e && (n = t.pastFuture(+this, n)), t.postformat(n)
            }

            function fa() {
                var e, t, n, a = vo(this._milliseconds) / 1e3, i = vo(this._days), o = vo(this._months);
                e = w(a / 60), t = w(e / 60), a %= 60, e %= 60, n = w(o / 12), o %= 12;
                var r = n, s = o, l = i, d = t, c = e, u = a, m = this.asSeconds();
                return m ? (m < 0 ? "-" : "") + "P" + (r ? r + "Y" : "") + (s ? s + "M" : "") + (l ? l + "D" : "") + (d || c || u ? "T" : "") + (d ? d + "H" : "") + (c ? c + "M" : "") + (u ? u + "S" : "") : "P0D"
            }

            var ga, ya;
            ya = Array.prototype.some ? Array.prototype.some : function (e) {
                for (var t = Object(this), n = t.length >>> 0, a = 0; a < n; a++)if (a in t && e.call(this, t[a], a, t))return !0;
                return !1
            };
            var va = ya, ba = t.momentProperties = [], wa = !1, Ma = {};
            t.suppressDeprecationWarnings = !1, t.deprecationHandler = null;
            var ka;
            ka = Object.keys ? Object.keys : function (e) {
                var t, n = [];
                for (t in e)c(e, t) && n.push(t);
                return n
            };
            var La, xa = ka, Ya = {
                sameDay: "[Today at] LT",
                nextDay: "[Tomorrow at] LT",
                nextWeek: "dddd [at] LT",
                lastDay: "[Yesterday at] LT",
                lastWeek: "[Last] dddd [at] LT",
                sameElse: "L"
            }, Da = {
                LTS: "h:mm:ss A",
                LT: "h:mm A",
                L: "MM/DD/YYYY",
                LL: "MMMM D, YYYY",
                LLL: "MMMM D, YYYY h:mm A",
                LLLL: "dddd, MMMM D, YYYY h:mm A"
            }, Ta = "Invalid date", Sa = "%d", za = /\d{1,2}/, Ha = {
                future: "in %s",
                past: "%s ago",
                s: "a few seconds",
                m: "a minute",
                mm: "%d minutes",
                h: "an hour",
                hh: "%d hours",
                d: "a day",
                dd: "%d days",
                M: "a month",
                MM: "%d months",
                y: "a year",
                yy: "%d years"
            }, ja = {}, Fa = {}, Pa = /(\[[^\[]*\])|(\\)?([Hh]mm(ss)?|Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|Qo?|YYYYYY|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|kk?|mm?|ss?|S{1,9}|x|X|zz?|ZZ?|.)/g, Ca = /(\[[^\[]*\])|(\\)?(LTS|LT|LL?L?L?|l{1,4})/g, Ea = {}, Aa = {}, Ba = /\d/, Ia = /\d\d/, Wa = /\d{3}/, Oa = /\d{4}/, Ra = /[+-]?\d{6}/, Na = /\d\d?/, Va = /\d\d\d\d?/, Ja = /\d\d\d\d\d\d?/, Ua = /\d{1,3}/, qa = /\d{1,4}/, Ga = /[+-]?\d{1,6}/, $a = /\d+/, Ka = /[+-]?\d+/, Xa = /Z|[+-]\d\d:?\d\d/gi, Za = /Z|[+-]\d\d(?::?\d\d)?/gi, Qa = /[+-]?\d+(\.\d{1,3})?/, ei = /[0-9]*['a-z\u00A0-\u05FF\u0700-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+|[\u0600-\u06FF\/]+(\s*?[\u0600-\u06FF]+){1,2}/i, ti = {}, ni = {}, ai = 0, ii = 1, oi = 2, ri = 3, si = 4, li = 5, di = 6, ci = 7, ui = 8;
            La = Array.prototype.indexOf ? Array.prototype.indexOf : function (e) {
                var t;
                for (t = 0; t < this.length; ++t)if (this[t] === e)return t;
                return -1
            };
            var mi = La;
            G("M", ["MM", 2], "Mo", function () {
                return this.month() + 1
            }), G("MMM", 0, 0, function (e) {
                return this.localeData().monthsShort(this, e)
            }), G("MMMM", 0, 0, function (e) {
                return this.localeData().months(this, e)
            }), A("month", "M"), W("month", 8), Q("M", Na), Q("MM", Na, Ia), Q("MMM", function (e, t) {
                return t.monthsShortRegex(e)
            }), Q("MMMM", function (e, t) {
                return t.monthsRegex(e)
            }), ae(["M", "MM"], function (e, t) {
                t[ii] = M(e) - 1
            }), ae(["MMM", "MMMM"], function (e, t, n, a) {
                var i = n._locale.monthsParse(e, a, n._strict);
                null != i ? t[ii] = i : p(n).invalidMonth = e
            });
            var _i = /D[oD]?(\[[^\[\]]*\]|\s)+MMMM?/, pi = "January_February_March_April_May_June_July_August_September_October_November_December".split("_"), hi = "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"), fi = ei, gi = ei;
            G("Y", 0, 0, function () {
                var e = this.year();
                return e <= 9999 ? "" + e : "+" + e
            }), G(0, ["YY", 2], 0, function () {
                return this.year() % 100
            }), G(0, ["YYYY", 4], 0, "year"), G(0, ["YYYYY", 5], 0, "year"), G(0, ["YYYYYY", 6, !0], 0, "year"), A("year", "y"), W("year", 1), Q("Y", Ka), Q("YY", Na, Ia), Q("YYYY", qa, Oa), Q("YYYYY", Ga, Ra), Q("YYYYYY", Ga, Ra), ae(["YYYYY", "YYYYYY"], ai), ae("YYYY", function (e, n) {
                n[ai] = 2 === e.length ? t.parseTwoDigitYear(e) : M(e)
            }), ae("YY", function (e, n) {
                n[ai] = t.parseTwoDigitYear(e)
            }), ae("Y", function (e, t) {
                t[ai] = parseInt(e, 10)
            }), t.parseTwoDigitYear = function (e) {
                return M(e) + (M(e) > 68 ? 1900 : 2e3)
            };
            var yi = R("FullYear", !0);
            G("w", ["ww", 2], "wo", "week"), G("W", ["WW", 2], "Wo", "isoWeek"), A("week", "w"), A("isoWeek", "W"), W("week", 5), W("isoWeek", 5), Q("w", Na), Q("ww", Na, Ia), Q("W", Na), Q("WW", Na, Ia), ie(["w", "ww", "W", "WW"], function (e, t, n, a) {
                t[a.substr(0, 1)] = M(e)
            });
            var vi = {dow: 0, doy: 6};
            G("d", 0, "do", "day"), G("dd", 0, 0, function (e) {
                return this.localeData().weekdaysMin(this, e)
            }), G("ddd", 0, 0, function (e) {
                return this.localeData().weekdaysShort(this, e)
            }), G("dddd", 0, 0, function (e) {
                return this.localeData().weekdays(this, e)
            }), G("e", 0, 0, "weekday"), G("E", 0, 0, "isoWeekday"), A("day", "d"), A("weekday", "e"), A("isoWeekday", "E"), W("day", 11), W("weekday", 11), W("isoWeekday", 11), Q("d", Na), Q("e", Na), Q("E", Na), Q("dd", function (e, t) {
                return t.weekdaysMinRegex(e)
            }), Q("ddd", function (e, t) {
                return t.weekdaysShortRegex(e)
            }), Q("dddd", function (e, t) {
                return t.weekdaysRegex(e)
            }), ie(["dd", "ddd", "dddd"], function (e, t, n, a) {
                var i = n._locale.weekdaysParse(e, a, n._strict);
                null != i ? t.d = i : p(n).invalidWeekday = e
            }), ie(["d", "e", "E"], function (e, t, n, a) {
                t[a] = M(e)
            });
            var bi = "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"), wi = "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"), Mi = "Su_Mo_Tu_We_Th_Fr_Sa".split("_"), ki = ei, Li = ei, xi = ei;
            G("H", ["HH", 2], 0, "hour"), G("h", ["hh", 2], 0, Je), G("k", ["kk", 2], 0, Ue), G("hmm", 0, 0, function () {
                return "" + Je.apply(this) + q(this.minutes(), 2)
            }), G("hmmss", 0, 0, function () {
                return "" + Je.apply(this) + q(this.minutes(), 2) + q(this.seconds(), 2)
            }), G("Hmm", 0, 0, function () {
                return "" + this.hours() + q(this.minutes(), 2)
            }), G("Hmmss", 0, 0, function () {
                return "" + this.hours() + q(this.minutes(), 2) + q(this.seconds(), 2)
            }), qe("a", !0), qe("A", !1), A("hour", "h"), W("hour", 13), Q("a", Ge), Q("A", Ge), Q("H", Na), Q("h", Na), Q("HH", Na, Ia), Q("hh", Na, Ia), Q("hmm", Va), Q("hmmss", Ja), Q("Hmm", Va), Q("Hmmss", Ja), ae(["H", "HH"], ri), ae(["a", "A"], function (e, t, n) {
                n._isPm = n._locale.isPM(e), n._meridiem = e
            }), ae(["h", "hh"], function (e, t, n) {
                t[ri] = M(e), p(n).bigHour = !0
            }), ae("hmm", function (e, t, n) {
                var a = e.length - 2;
                t[ri] = M(e.substr(0, a)), t[si] = M(e.substr(a)), p(n).bigHour = !0
            }), ae("hmmss", function (e, t, n) {
                var a = e.length - 4, i = e.length - 2;
                t[ri] = M(e.substr(0, a)), t[si] = M(e.substr(a, 2)), t[li] = M(e.substr(i)), p(n).bigHour = !0
            }), ae("Hmm", function (e, t, n) {
                var a = e.length - 2;
                t[ri] = M(e.substr(0, a)), t[si] = M(e.substr(a))
            }), ae("Hmmss", function (e, t, n) {
                var a = e.length - 4, i = e.length - 2;
                t[ri] = M(e.substr(0, a)), t[si] = M(e.substr(a, 2)), t[li] = M(e.substr(i))
            });
            var Yi, Di = /[ap]\.?m?\.?/i, Ti = R("Hours", !0), Si = {
                calendar: Ya,
                longDateFormat: Da,
                invalidDate: Ta,
                ordinal: Sa,
                ordinalParse: za,
                relativeTime: Ha,
                months: pi,
                monthsShort: hi,
                week: vi,
                weekdays: bi,
                weekdaysMin: Mi,
                weekdaysShort: wi,
                meridiemParse: Di
            }, zi = {}, Hi = {}, ji = /^\s*((?:[+-]\d{6}|\d{4})-(?:\d\d-\d\d|W\d\d-\d|W\d\d|\d\d\d|\d\d))(?:(T| )(\d\d(?::\d\d(?::\d\d(?:[.,]\d+)?)?)?)([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/, Fi = /^\s*((?:[+-]\d{6}|\d{4})(?:\d\d\d\d|W\d\d\d|W\d\d|\d\d\d|\d\d))(?:(T| )(\d\d(?:\d\d(?:\d\d(?:[.,]\d+)?)?)?)([\+\-]\d\d(?::?\d\d)?|\s*Z)?)?$/, Pi = /Z|[+-]\d\d(?::?\d\d)?/, Ci = [["YYYYYY-MM-DD", /[+-]\d{6}-\d\d-\d\d/], ["YYYY-MM-DD", /\d{4}-\d\d-\d\d/], ["GGGG-[W]WW-E", /\d{4}-W\d\d-\d/], ["GGGG-[W]WW", /\d{4}-W\d\d/, !1], ["YYYY-DDD", /\d{4}-\d{3}/], ["YYYY-MM", /\d{4}-\d\d/, !1], ["YYYYYYMMDD", /[+-]\d{10}/], ["YYYYMMDD", /\d{8}/], ["GGGG[W]WWE", /\d{4}W\d{3}/], ["GGGG[W]WW", /\d{4}W\d{2}/, !1], ["YYYYDDD", /\d{7}/]], Ei = [["HH:mm:ss.SSSS", /\d\d:\d\d:\d\d\.\d+/], ["HH:mm:ss,SSSS", /\d\d:\d\d:\d\d,\d+/], ["HH:mm:ss", /\d\d:\d\d:\d\d/], ["HH:mm", /\d\d:\d\d/], ["HHmmss.SSSS", /\d\d\d\d\d\d\.\d+/], ["HHmmss,SSSS", /\d\d\d\d\d\d,\d+/], ["HHmmss", /\d\d\d\d\d\d/], ["HHmm", /\d\d\d\d/], ["HH", /\d\d/]], Ai = /^\/?Date\((\-?\d+)/i;
            t.createFromInputFallback = x("value provided is not in a recognized ISO format. moment construction falls back to js Date(), which is not reliable across all browsers and versions. Non ISO date formats are discouraged and will be removed in an upcoming major release. Please refer to http://momentjs.com/guides/#/warnings/js-date/ for more info.", function (e) {
                e._d = new Date(e._i + (e._useUTC ? " UTC" : ""))
            }), t.ISO_8601 = function () {
            };
            var Bi = x("moment().min is deprecated, use moment.max instead. http://momentjs.com/guides/#/warnings/min-max/", function () {
                var e = bt.apply(null, arguments);
                return this.isValid() && e.isValid() ? e < this ? this : e : f()
            }), Ii = x("moment().max is deprecated, use moment.min instead. http://momentjs.com/guides/#/warnings/min-max/", function () {
                var e = bt.apply(null, arguments);
                return this.isValid() && e.isValid() ? e > this ? this : e : f()
            }), Wi = function () {
                return Date.now ? Date.now() : +new Date
            };
            Dt("Z", ":"), Dt("ZZ", ""), Q("Z", Za), Q("ZZ", Za), ae(["Z", "ZZ"], function (e, t, n) {
                n._useUTC = !0, n._tzm = Tt(Za, e)
            });
            var Oi = /([\+\-]|\d\d)/gi;
            t.updateOffset = function () {
            };
            var Ri = /^(\-)?(?:(\d*)[. ])?(\d+)\:(\d+)(?:\:(\d+)(\.\d*)?)?$/, Ni = /^(-)?P(?:(-?[0-9,.]*)Y)?(?:(-?[0-9,.]*)M)?(?:(-?[0-9,.]*)W)?(?:(-?[0-9,.]*)D)?(?:T(?:(-?[0-9,.]*)H)?(?:(-?[0-9,.]*)M)?(?:(-?[0-9,.]*)S)?)?$/;
            Rt.fn = Lt.prototype;
            var Vi = Ut(1, "add"), Ji = Ut(-1, "subtract");
            t.defaultFormat = "YYYY-MM-DDTHH:mm:ssZ", t.defaultFormatUtc = "YYYY-MM-DDTHH:mm:ss[Z]";
            var Ui = x("moment().lang() is deprecated. Instead, use moment().localeData() to get the language configuration. Use moment().locale() to change languages.", function (e) {
                return void 0 === e ? this.localeData() : this.locale(e)
            });
            G(0, ["gg", 2], 0, function () {
                return this.weekYear() % 100
            }), G(0, ["GG", 2], 0, function () {
                return this.isoWeekYear() % 100
            }), Tn("gggg", "weekYear"), Tn("ggggg", "weekYear"), Tn("GGGG", "isoWeekYear"), Tn("GGGGG", "isoWeekYear"), A("weekYear", "gg"), A("isoWeekYear", "GG"), W("weekYear", 1), W("isoWeekYear", 1),
                Q("G", Ka), Q("g", Ka), Q("GG", Na, Ia), Q("gg", Na, Ia), Q("GGGG", qa, Oa), Q("gggg", qa, Oa), Q("GGGGG", Ga, Ra), Q("ggggg", Ga, Ra), ie(["gggg", "ggggg", "GGGG", "GGGGG"], function (e, t, n, a) {
                t[a.substr(0, 2)] = M(e)
            }), ie(["gg", "GG"], function (e, n, a, i) {
                n[i] = t.parseTwoDigitYear(e)
            }), G("Q", 0, "Qo", "quarter"), A("quarter", "Q"), W("quarter", 7), Q("Q", Ba), ae("Q", function (e, t) {
                t[ii] = 3 * (M(e) - 1)
            }), G("D", ["DD", 2], "Do", "date"), A("date", "D"), W("date", 9), Q("D", Na), Q("DD", Na, Ia), Q("Do", function (e, t) {
                return e ? t._ordinalParse : t._ordinalParseLenient
            }), ae(["D", "DD"], oi), ae("Do", function (e, t) {
                t[oi] = M(e.match(Na)[0], 10)
            });
            var qi = R("Date", !0);
            G("DDD", ["DDDD", 3], "DDDo", "dayOfYear"), A("dayOfYear", "DDD"), W("dayOfYear", 4), Q("DDD", Ua), Q("DDDD", Wa), ae(["DDD", "DDDD"], function (e, t, n) {
                n._dayOfYear = M(e)
            }), G("m", ["mm", 2], 0, "minute"), A("minute", "m"), W("minute", 14), Q("m", Na), Q("mm", Na, Ia), ae(["m", "mm"], si);
            var Gi = R("Minutes", !1);
            G("s", ["ss", 2], 0, "second"), A("second", "s"), W("second", 15), Q("s", Na), Q("ss", Na, Ia), ae(["s", "ss"], li);
            var $i = R("Seconds", !1);
            G("S", 0, 0, function () {
                return ~~(this.millisecond() / 100)
            }), G(0, ["SS", 2], 0, function () {
                return ~~(this.millisecond() / 10)
            }), G(0, ["SSS", 3], 0, "millisecond"), G(0, ["SSSS", 4], 0, function () {
                return 10 * this.millisecond()
            }), G(0, ["SSSSS", 5], 0, function () {
                return 100 * this.millisecond()
            }), G(0, ["SSSSSS", 6], 0, function () {
                return 1e3 * this.millisecond()
            }), G(0, ["SSSSSSS", 7], 0, function () {
                return 1e4 * this.millisecond()
            }), G(0, ["SSSSSSSS", 8], 0, function () {
                return 1e5 * this.millisecond()
            }), G(0, ["SSSSSSSSS", 9], 0, function () {
                return 1e6 * this.millisecond()
            }), A("millisecond", "ms"), W("millisecond", 16), Q("S", Ua, Ba), Q("SS", Ua, Ia), Q("SSS", Ua, Wa);
            var Ki;
            for (Ki = "SSSS"; Ki.length <= 9; Ki += "S")Q(Ki, $a);
            for (Ki = "S"; Ki.length <= 9; Ki += "S")ae(Ki, An);
            var Xi = R("Milliseconds", !1);
            G("z", 0, 0, "zoneAbbr"), G("zz", 0, 0, "zoneName");
            var Zi = v.prototype;
            Zi.add = Vi, Zi.calendar = $t, Zi.clone = Kt, Zi.diff = an, Zi.endOf = gn, Zi.format = dn, Zi.from = cn, Zi.fromNow = un, Zi.to = mn, Zi.toNow = _n, Zi.get = J, Zi.invalidAt = Yn, Zi.isAfter = Xt, Zi.isBefore = Zt, Zi.isBetween = Qt, Zi.isSame = en, Zi.isSameOrAfter = tn, Zi.isSameOrBefore = nn, Zi.isValid = Ln, Zi.lang = Ui, Zi.locale = pn, Zi.localeData = hn, Zi.max = Ii, Zi.min = Bi, Zi.parsingFlags = xn, Zi.set = U, Zi.startOf = fn, Zi.subtract = Ji, Zi.toArray = wn, Zi.toObject = Mn, Zi.toDate = bn, Zi.toISOString = sn, Zi.inspect = ln, Zi.toJSON = kn, Zi.toString = rn, Zi.unix = vn, Zi.valueOf = yn, Zi.creationData = Dn, Zi.year = yi, Zi.isLeapYear = ve, Zi.weekYear = Sn, Zi.isoWeekYear = zn, Zi.quarter = Zi.quarters = Cn, Zi.month = me, Zi.daysInMonth = _e, Zi.week = Zi.weeks = Se, Zi.isoWeek = Zi.isoWeeks = ze, Zi.weeksInYear = jn, Zi.isoWeeksInYear = Hn, Zi.date = qi, Zi.day = Zi.days = Be, Zi.weekday = Ie, Zi.isoWeekday = We, Zi.dayOfYear = En, Zi.hour = Zi.hours = Ti, Zi.minute = Zi.minutes = Gi, Zi.second = Zi.seconds = $i, Zi.millisecond = Zi.milliseconds = Xi, Zi.utcOffset = Ht, Zi.utc = Ft, Zi.local = Pt, Zi.parseZone = Ct, Zi.hasAlignedHourOffset = Et, Zi.isDST = At, Zi.isLocal = It, Zi.isUtcOffset = Wt, Zi.isUtc = Ot, Zi.isUTC = Ot, Zi.zoneAbbr = Bn, Zi.zoneName = In, Zi.dates = x("dates accessor is deprecated. Use date instead.", qi), Zi.months = x("months accessor is deprecated. Use month instead", me), Zi.years = x("years accessor is deprecated. Use year instead", yi), Zi.zone = x("moment().zone is deprecated, use moment().utcOffset instead. http://momentjs.com/guides/#/warnings/zone/", jt), Zi.isDSTShifted = x("isDSTShifted is deprecated. See http://momentjs.com/guides/#/warnings/dst-shifted/ for more information", Bt);
            var Qi = z.prototype;
            Qi.calendar = H, Qi.longDateFormat = j, Qi.invalidDate = F, Qi.ordinal = P, Qi.preparse = Rn, Qi.postformat = Rn, Qi.relativeTime = C, Qi.pastFuture = E, Qi.set = T, Qi.months = se, Qi.monthsShort = le, Qi.monthsParse = ce, Qi.monthsRegex = he, Qi.monthsShortRegex = pe, Qi.week = Ye, Qi.firstDayOfYear = Te, Qi.firstDayOfWeek = De, Qi.weekdays = Fe, Qi.weekdaysMin = Ce, Qi.weekdaysShort = Pe, Qi.weekdaysParse = Ae, Qi.weekdaysRegex = Oe, Qi.weekdaysShortRegex = Re, Qi.weekdaysMinRegex = Ne, Qi.isPM = $e, Qi.meridiem = Ke, et("en", {
                ordinalParse: /\d{1,2}(th|st|nd|rd)/,
                ordinal: function (e) {
                    var t = e % 10, n = 1 === M(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                    return e + n
                }
            }), t.lang = x("moment.lang is deprecated. Use moment.locale instead.", et), t.langData = x("moment.langData is deprecated. Use moment.localeData instead.", at);
            var eo = Math.abs, to = sa("ms"), no = sa("s"), ao = sa("m"), io = sa("h"), oo = sa("d"), ro = sa("w"), so = sa("M"), lo = sa("y"), co = da("milliseconds"), uo = da("seconds"), mo = da("minutes"), _o = da("hours"), po = da("days"), ho = da("months"), fo = da("years"), go = Math.round, yo = {
                s: 45,
                m: 45,
                h: 22,
                d: 26,
                M: 11
            }, vo = Math.abs, bo = Lt.prototype;
            return bo.abs = Xn, bo.add = Qn, bo.subtract = ea, bo.as = oa, bo.asMilliseconds = to, bo.asSeconds = no, bo.asMinutes = ao, bo.asHours = io, bo.asDays = oo, bo.asWeeks = ro, bo.asMonths = so, bo.asYears = lo, bo.valueOf = ra, bo._bubble = na, bo.get = la, bo.milliseconds = co, bo.seconds = uo, bo.minutes = mo, bo.hours = _o, bo.days = po, bo.weeks = ca, bo.months = ho, bo.years = fo, bo.humanize = ha, bo.toISOString = fa, bo.toString = fa, bo.toJSON = fa, bo.locale = pn, bo.localeData = hn, bo.toIsoString = x("toIsoString() is deprecated. Please use toISOString() instead (notice the capitals)", fa), bo.lang = Ui, G("X", 0, 0, "unix"), G("x", 0, 0, "valueOf"), Q("x", Ka), Q("X", Qa), ae("X", function (e, t, n) {
                n._d = new Date(1e3 * parseFloat(e, 10))
            }), ae("x", function (e, t, n) {
                n._d = new Date(M(e))
            }), t.version = "2.17.1", a(bt), t.fn = Zi, t.min = Mt, t.max = kt, t.now = Wi, t.utc = m, t.unix = Wn, t.months = Un, t.isDate = l, t.locale = et, t.invalid = f, t.duration = Rt, t.isMoment = b, t.weekdays = Gn, t.parseZone = On, t.localeData = at, t.isDuration = xt, t.monthsShort = qn, t.weekdaysMin = Kn, t.defineLocale = tt, t.updateLocale = nt, t.locales = it, t.weekdaysShort = $n, t.normalizeUnits = B, t.relativeTimeRounding = _a, t.relativeTimeThreshold = pa, t.calendarFormat = Gt, t.prototype = Zi, t
        })
    }).call(t, n(52)(e))
}, function (e, t) {
    e.exports = function (e) {
        return e.webpackPolyfill || (e.deprecate = function () {
        }, e.paths = [], e.children = [], e.webpackPolyfill = 1), e
    }
}, function (e, t, n) {
    function a(e) {
        return n(i(e))
    }

    function i(e) {
        return o[e] || function () {
                throw new Error("Cannot find module '" + e + "'.")
            }()
    }

    var o = {
        "./af": 54,
        "./af.js": 54,
        "./ar": 55,
        "./ar-dz": 56,
        "./ar-dz.js": 56,
        "./ar-ly": 57,
        "./ar-ly.js": 57,
        "./ar-ma": 58,
        "./ar-ma.js": 58,
        "./ar-sa": 59,
        "./ar-sa.js": 59,
        "./ar-tn": 60,
        "./ar-tn.js": 60,
        "./ar.js": 55,
        "./az": 61,
        "./az.js": 61,
        "./be": 62,
        "./be.js": 62,
        "./bg": 63,
        "./bg.js": 63,
        "./bn": 64,
        "./bn.js": 64,
        "./bo": 65,
        "./bo.js": 65,
        "./br": 66,
        "./br.js": 66,
        "./bs": 67,
        "./bs.js": 67,
        "./ca": 68,
        "./ca.js": 68,
        "./cs": 69,
        "./cs.js": 69,
        "./cv": 70,
        "./cv.js": 70,
        "./cy": 71,
        "./cy.js": 71,
        "./da": 72,
        "./da.js": 72,
        "./de": 73,
        "./de-at": 74,
        "./de-at.js": 74,
        "./de.js": 73,
        "./dv": 75,
        "./dv.js": 75,
        "./el": 76,
        "./el.js": 76,
        "./en-au": 77,
        "./en-au.js": 77,
        "./en-ca": 78,
        "./en-ca.js": 78,
        "./en-gb": 79,
        "./en-gb.js": 79,
        "./en-ie": 80,
        "./en-ie.js": 80,
        "./en-nz": 81,
        "./en-nz.js": 81,
        "./eo": 82,
        "./eo.js": 82,
        "./es": 83,
        "./es-do": 84,
        "./es-do.js": 84,
        "./es.js": 83,
        "./et": 85,
        "./et.js": 85,
        "./eu": 86,
        "./eu.js": 86,
        "./fa": 87,
        "./fa.js": 87,
        "./fi": 88,
        "./fi.js": 88,
        "./fo": 89,
        "./fo.js": 89,
        "./fr": 90,
        "./fr-ca": 91,
        "./fr-ca.js": 91,
        "./fr-ch": 92,
        "./fr-ch.js": 92,
        "./fr.js": 90,
        "./fy": 93,
        "./fy.js": 93,
        "./gd": 94,
        "./gd.js": 94,
        "./gl": 95,
        "./gl.js": 95,
        "./he": 96,
        "./he.js": 96,
        "./hi": 97,
        "./hi.js": 97,
        "./hr": 98,
        "./hr.js": 98,
        "./hu": 99,
        "./hu.js": 99,
        "./hy-am": 100,
        "./hy-am.js": 100,
        "./id": 101,
        "./id.js": 101,
        "./is": 102,
        "./is.js": 102,
        "./it": 103,
        "./it.js": 103,
        "./ja": 104,
        "./ja.js": 104,
        "./jv": 105,
        "./jv.js": 105,
        "./ka": 106,
        "./ka.js": 106,
        "./kk": 107,
        "./kk.js": 107,
        "./km": 108,
        "./km.js": 108,
        "./ko": 109,
        "./ko.js": 109,
        "./ky": 110,
        "./ky.js": 110,
        "./lb": 111,
        "./lb.js": 111,
        "./lo": 112,
        "./lo.js": 112,
        "./lt": 113,
        "./lt.js": 113,
        "./lv": 114,
        "./lv.js": 114,
        "./me": 115,
        "./me.js": 115,
        "./mi": 116,
        "./mi.js": 116,
        "./mk": 117,
        "./mk.js": 117,
        "./ml": 118,
        "./ml.js": 118,
        "./mr": 119,
        "./mr.js": 119,
        "./ms": 120,
        "./ms-my": 121,
        "./ms-my.js": 121,
        "./ms.js": 120,
        "./my": 122,
        "./my.js": 122,
        "./nb": 123,
        "./nb.js": 123,
        "./ne": 124,
        "./ne.js": 124,
        "./nl": 125,
        "./nl-be": 126,
        "./nl-be.js": 126,
        "./nl.js": 125,
        "./nn": 127,
        "./nn.js": 127,
        "./pa-in": 128,
        "./pa-in.js": 128,
        "./pl": 129,
        "./pl.js": 129,
        "./pt": 130,
        "./pt-br": 131,
        "./pt-br.js": 131,
        "./pt.js": 130,
        "./ro": 132,
        "./ro.js": 132,
        "./ru": 133,
        "./ru.js": 133,
        "./se": 134,
        "./se.js": 134,
        "./si": 135,
        "./si.js": 135,
        "./sk": 136,
        "./sk.js": 136,
        "./sl": 137,
        "./sl.js": 137,
        "./sq": 138,
        "./sq.js": 138,
        "./sr": 139,
        "./sr-cyrl": 140,
        "./sr-cyrl.js": 140,
        "./sr.js": 139,
        "./ss": 141,
        "./ss.js": 141,
        "./sv": 142,
        "./sv.js": 142,
        "./sw": 143,
        "./sw.js": 143,
        "./ta": 144,
        "./ta.js": 144,
        "./te": 145,
        "./te.js": 145,
        "./tet": 146,
        "./tet.js": 146,
        "./th": 147,
        "./th.js": 147,
        "./tl-ph": 148,
        "./tl-ph.js": 148,
        "./tlh": 149,
        "./tlh.js": 149,
        "./tr": 150,
        "./tr.js": 150,
        "./tzl": 151,
        "./tzl.js": 151,
        "./tzm": 152,
        "./tzm-latn": 153,
        "./tzm-latn.js": 153,
        "./tzm.js": 152,
        "./uk": 154,
        "./uk.js": 154,
        "./uz": 155,
        "./uz.js": 155,
        "./vi": 156,
        "./vi.js": 156,
        "./x-pseudo": 157,
        "./x-pseudo.js": 157,
        "./yo": 158,
        "./yo.js": 158,
        "./zh-cn": 159,
        "./zh-cn.js": 159,
        "./zh-hk": 160,
        "./zh-hk.js": 160,
        "./zh-tw": 161,
        "./zh-tw.js": 161
    };
    a.keys = function () {
        return Object.keys(o)
    }, a.resolve = i, e.exports = a, a.id = 53
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("af", {
            months: "Januarie_Februarie_Maart_April_Mei_Junie_Julie_Augustus_September_Oktober_November_Desember".split("_"),
            monthsShort: "Jan_Feb_Mrt_Apr_Mei_Jun_Jul_Aug_Sep_Okt_Nov_Des".split("_"),
            weekdays: "Sondag_Maandag_Dinsdag_Woensdag_Donderdag_Vrydag_Saterdag".split("_"),
            weekdaysShort: "Son_Maa_Din_Woe_Don_Vry_Sat".split("_"),
            weekdaysMin: "So_Ma_Di_Wo_Do_Vr_Sa".split("_"),
            meridiemParse: /vm|nm/i,
            isPM: function (e) {
                return /^nm$/i.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 12 ? n ? "vm" : "VM" : n ? "nm" : "NM"
            },
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Vandag om] LT",
                nextDay: "[Môre om] LT",
                nextWeek: "dddd [om] LT",
                lastDay: "[Gister om] LT",
                lastWeek: "[Laas] dddd [om] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "oor %s",
                past: "%s gelede",
                s: "'n paar sekondes",
                m: "'n minuut",
                mm: "%d minute",
                h: "'n uur",
                hh: "%d ure",
                d: "'n dag",
                dd: "%d dae",
                M: "'n maand",
                MM: "%d maande",
                y: "'n jaar",
                yy: "%d jaar"
            },
            ordinalParse: /\d{1,2}(ste|de)/,
            ordinal: function (e) {
                return e + (1 === e || 8 === e || e >= 20 ? "ste" : "de")
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "١", 2: "٢", 3: "٣", 4: "٤", 5: "٥", 6: "٦", 7: "٧", 8: "٨", 9: "٩", 0: "٠"}, n = {
            "١": "1",
            "٢": "2",
            "٣": "3",
            "٤": "4",
            "٥": "5",
            "٦": "6",
            "٧": "7",
            "٨": "8",
            "٩": "9",
            "٠": "0"
        }, a = function (e) {
            return 0 === e ? 0 : 1 === e ? 1 : 2 === e ? 2 : e % 100 >= 3 && e % 100 <= 10 ? 3 : e % 100 >= 11 ? 4 : 5
        }, i = {
            s: ["أقل من ثانية", "ثانية واحدة", ["ثانيتان", "ثانيتين"], "%d ثوان", "%d ثانية", "%d ثانية"],
            m: ["أقل من دقيقة", "دقيقة واحدة", ["دقيقتان", "دقيقتين"], "%d دقائق", "%d دقيقة", "%d دقيقة"],
            h: ["أقل من ساعة", "ساعة واحدة", ["ساعتان", "ساعتين"], "%d ساعات", "%d ساعة", "%d ساعة"],
            d: ["أقل من يوم", "يوم واحد", ["يومان", "يومين"], "%d أيام", "%d يومًا", "%d يوم"],
            M: ["أقل من شهر", "شهر واحد", ["شهران", "شهرين"], "%d أشهر", "%d شهرا", "%d شهر"],
            y: ["أقل من عام", "عام واحد", ["عامان", "عامين"], "%d أعوام", "%d عامًا", "%d عام"]
        }, o = function (e) {
            return function (t, n, o, r) {
                var s = a(t), l = i[e][a(t)];
                return 2 === s && (l = l[n ? 0 : 1]), l.replace(/%d/i, t)
            }
        }, r = ["كانون الثاني يناير", "شباط فبراير", "آذار مارس", "نيسان أبريل", "أيار مايو", "حزيران يونيو", "تموز يوليو", "آب أغسطس", "أيلول سبتمبر", "تشرين الأول أكتوبر", "تشرين الثاني نوفمبر", "كانون الأول ديسمبر"], s = e.defineLocale("ar", {
            months: r,
            monthsShort: r,
            weekdays: "الأحد_الإثنين_الثلاثاء_الأربعاء_الخميس_الجمعة_السبت".split("_"),
            weekdaysShort: "أحد_إثنين_ثلاثاء_أربعاء_خميس_جمعة_سبت".split("_"),
            weekdaysMin: "ح_ن_ث_ر_خ_ج_س".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "D/‏M/‏YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            meridiemParse: /ص|م/,
            isPM: function (e) {
                return "م" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "ص" : "م"
            },
            calendar: {
                sameDay: "[اليوم عند الساعة] LT",
                nextDay: "[غدًا عند الساعة] LT",
                nextWeek: "dddd [عند الساعة] LT",
                lastDay: "[أمس عند الساعة] LT",
                lastWeek: "dddd [عند الساعة] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "بعد %s",
                past: "منذ %s",
                s: o("s"),
                m: o("m"),
                mm: o("m"),
                h: o("h"),
                hh: o("h"),
                d: o("d"),
                dd: o("d"),
                M: o("M"),
                MM: o("M"),
                y: o("y"),
                yy: o("y")
            },
            preparse: function (e) {
                return e.replace(/\u200f/g, "").replace(/[١٢٣٤٥٦٧٨٩٠]/g, function (e) {
                    return n[e]
                }).replace(/،/g, ",")
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                }).replace(/,/g, "،")
            },
            week: {dow: 6, doy: 12}
        });
        return s
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ar-dz", {
            months: "جانفي_فيفري_مارس_أفريل_ماي_جوان_جويلية_أوت_سبتمبر_أكتوبر_نوفمبر_ديسمبر".split("_"),
            monthsShort: "جانفي_فيفري_مارس_أفريل_ماي_جوان_جويلية_أوت_سبتمبر_أكتوبر_نوفمبر_ديسمبر".split("_"),
            weekdays: "الأحد_الإثنين_الثلاثاء_الأربعاء_الخميس_الجمعة_السبت".split("_"),
            weekdaysShort: "احد_اثنين_ثلاثاء_اربعاء_خميس_جمعة_سبت".split("_"),
            weekdaysMin: "أح_إث_ثلا_أر_خم_جم_سب".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[اليوم على الساعة] LT",
                nextDay: "[غدا على الساعة] LT",
                nextWeek: "dddd [على الساعة] LT",
                lastDay: "[أمس على الساعة] LT",
                lastWeek: "dddd [على الساعة] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "في %s",
                past: "منذ %s",
                s: "ثوان",
                m: "دقيقة",
                mm: "%d دقائق",
                h: "ساعة",
                hh: "%d ساعات",
                d: "يوم",
                dd: "%d أيام",
                M: "شهر",
                MM: "%d أشهر",
                y: "سنة",
                yy: "%d سنوات"
            },
            week: {dow: 0, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "1", 2: "2", 3: "3", 4: "4", 5: "5", 6: "6", 7: "7", 8: "8", 9: "9", 0: "0"}, n = function (e) {
            return 0 === e ? 0 : 1 === e ? 1 : 2 === e ? 2 : e % 100 >= 3 && e % 100 <= 10 ? 3 : e % 100 >= 11 ? 4 : 5
        }, a = {
            s: ["أقل من ثانية", "ثانية واحدة", ["ثانيتان", "ثانيتين"], "%d ثوان", "%d ثانية", "%d ثانية"],
            m: ["أقل من دقيقة", "دقيقة واحدة", ["دقيقتان", "دقيقتين"], "%d دقائق", "%d دقيقة", "%d دقيقة"],
            h: ["أقل من ساعة", "ساعة واحدة", ["ساعتان", "ساعتين"], "%d ساعات", "%d ساعة", "%d ساعة"],
            d: ["أقل من يوم", "يوم واحد", ["يومان", "يومين"], "%d أيام", "%d يومًا", "%d يوم"],
            M: ["أقل من شهر", "شهر واحد", ["شهران", "شهرين"], "%d أشهر", "%d شهرا", "%d شهر"],
            y: ["أقل من عام", "عام واحد", ["عامان", "عامين"], "%d أعوام", "%d عامًا", "%d عام"]
        }, i = function (e) {
            return function (t, i, o, r) {
                var s = n(t), l = a[e][n(t)];
                return 2 === s && (l = l[i ? 0 : 1]), l.replace(/%d/i, t)
            }
        }, o = ["يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"], r = e.defineLocale("ar-ly", {
            months: o,
            monthsShort: o,
            weekdays: "الأحد_الإثنين_الثلاثاء_الأربعاء_الخميس_الجمعة_السبت".split("_"),
            weekdaysShort: "أحد_إثنين_ثلاثاء_أربعاء_خميس_جمعة_سبت".split("_"),
            weekdaysMin: "ح_ن_ث_ر_خ_ج_س".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "D/‏M/‏YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            meridiemParse: /ص|م/,
            isPM: function (e) {
                return "م" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "ص" : "م"
            },
            calendar: {
                sameDay: "[اليوم عند الساعة] LT",
                nextDay: "[غدًا عند الساعة] LT",
                nextWeek: "dddd [عند الساعة] LT",
                lastDay: "[أمس عند الساعة] LT",
                lastWeek: "dddd [عند الساعة] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "بعد %s",
                past: "منذ %s",
                s: i("s"),
                m: i("m"),
                mm: i("m"),
                h: i("h"),
                hh: i("h"),
                d: i("d"),
                dd: i("d"),
                M: i("M"),
                MM: i("M"),
                y: i("y"),
                yy: i("y")
            },
            preparse: function (e) {
                return e.replace(/\u200f/g, "").replace(/،/g, ",")
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                }).replace(/,/g, "،")
            },
            week: {dow: 6, doy: 12}
        });
        return r
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ar-ma", {
            months: "يناير_فبراير_مارس_أبريل_ماي_يونيو_يوليوز_غشت_شتنبر_أكتوبر_نونبر_دجنبر".split("_"),
            monthsShort: "يناير_فبراير_مارس_أبريل_ماي_يونيو_يوليوز_غشت_شتنبر_أكتوبر_نونبر_دجنبر".split("_"),
            weekdays: "الأحد_الإتنين_الثلاثاء_الأربعاء_الخميس_الجمعة_السبت".split("_"),
            weekdaysShort: "احد_اتنين_ثلاثاء_اربعاء_خميس_جمعة_سبت".split("_"),
            weekdaysMin: "ح_ن_ث_ر_خ_ج_س".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[اليوم على الساعة] LT",
                nextDay: "[غدا على الساعة] LT",
                nextWeek: "dddd [على الساعة] LT",
                lastDay: "[أمس على الساعة] LT",
                lastWeek: "dddd [على الساعة] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "في %s",
                past: "منذ %s",
                s: "ثوان",
                m: "دقيقة",
                mm: "%d دقائق",
                h: "ساعة",
                hh: "%d ساعات",
                d: "يوم",
                dd: "%d أيام",
                M: "شهر",
                MM: "%d أشهر",
                y: "سنة",
                yy: "%d سنوات"
            },
            week: {dow: 6, doy: 12}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "١", 2: "٢", 3: "٣", 4: "٤", 5: "٥", 6: "٦", 7: "٧", 8: "٨", 9: "٩", 0: "٠"}, n = {
            "١": "1",
            "٢": "2",
            "٣": "3",
            "٤": "4",
            "٥": "5",
            "٦": "6",
            "٧": "7",
            "٨": "8",
            "٩": "9",
            "٠": "0"
        }, a = e.defineLocale("ar-sa", {
            months: "يناير_فبراير_مارس_أبريل_مايو_يونيو_يوليو_أغسطس_سبتمبر_أكتوبر_نوفمبر_ديسمبر".split("_"),
            monthsShort: "يناير_فبراير_مارس_أبريل_مايو_يونيو_يوليو_أغسطس_سبتمبر_أكتوبر_نوفمبر_ديسمبر".split("_"),
            weekdays: "الأحد_الإثنين_الثلاثاء_الأربعاء_الخميس_الجمعة_السبت".split("_"),
            weekdaysShort: "أحد_إثنين_ثلاثاء_أربعاء_خميس_جمعة_سبت".split("_"),
            weekdaysMin: "ح_ن_ث_ر_خ_ج_س".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            meridiemParse: /ص|م/,
            isPM: function (e) {
                return "م" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "ص" : "م"
            },
            calendar: {
                sameDay: "[اليوم على الساعة] LT",
                nextDay: "[غدا على الساعة] LT",
                nextWeek: "dddd [على الساعة] LT",
                lastDay: "[أمس على الساعة] LT",
                lastWeek: "dddd [على الساعة] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "في %s",
                past: "منذ %s",
                s: "ثوان",
                m: "دقيقة",
                mm: "%d دقائق",
                h: "ساعة",
                hh: "%d ساعات",
                d: "يوم",
                dd: "%d أيام",
                M: "شهر",
                MM: "%d أشهر",
                y: "سنة",
                yy: "%d سنوات"
            },
            preparse: function (e) {
                return e.replace(/[١٢٣٤٥٦٧٨٩٠]/g, function (e) {
                    return n[e]
                }).replace(/،/g, ",")
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                }).replace(/,/g, "،")
            },
            week: {dow: 0, doy: 6}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ar-tn", {
            months: "جانفي_فيفري_مارس_أفريل_ماي_جوان_جويلية_أوت_سبتمبر_أكتوبر_نوفمبر_ديسمبر".split("_"),
            monthsShort: "جانفي_فيفري_مارس_أفريل_ماي_جوان_جويلية_أوت_سبتمبر_أكتوبر_نوفمبر_ديسمبر".split("_"),
            weekdays: "الأحد_الإثنين_الثلاثاء_الأربعاء_الخميس_الجمعة_السبت".split("_"),
            weekdaysShort: "أحد_إثنين_ثلاثاء_أربعاء_خميس_جمعة_سبت".split("_"),
            weekdaysMin: "ح_ن_ث_ر_خ_ج_س".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[اليوم على الساعة] LT",
                nextDay: "[غدا على الساعة] LT",
                nextWeek: "dddd [على الساعة] LT",
                lastDay: "[أمس على الساعة] LT",
                lastWeek: "dddd [على الساعة] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "في %s",
                past: "منذ %s",
                s: "ثوان",
                m: "دقيقة",
                mm: "%d دقائق",
                h: "ساعة",
                hh: "%d ساعات",
                d: "يوم",
                dd: "%d أيام",
                M: "شهر",
                MM: "%d أشهر",
                y: "سنة",
                yy: "%d سنوات"
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {
            1: "-inci",
            5: "-inci",
            8: "-inci",
            70: "-inci",
            80: "-inci",
            2: "-nci",
            7: "-nci",
            20: "-nci",
            50: "-nci",
            3: "-üncü",
            4: "-üncü",
            100: "-üncü",
            6: "-ncı",
            9: "-uncu",
            10: "-uncu",
            30: "-uncu",
            60: "-ıncı",
            90: "-ıncı"
        }, n = e.defineLocale("az", {
            months: "yanvar_fevral_mart_aprel_may_iyun_iyul_avqust_sentyabr_oktyabr_noyabr_dekabr".split("_"),
            monthsShort: "yan_fev_mar_apr_may_iyn_iyl_avq_sen_okt_noy_dek".split("_"),
            weekdays: "Bazar_Bazar ertəsi_Çərşənbə axşamı_Çərşənbə_Cümə axşamı_Cümə_Şənbə".split("_"),
            weekdaysShort: "Baz_BzE_ÇAx_Çər_CAx_Cüm_Şən".split("_"),
            weekdaysMin: "Bz_BE_ÇA_Çə_CA_Cü_Şə".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[bugün saat] LT",
                nextDay: "[sabah saat] LT",
                nextWeek: "[gələn həftə] dddd [saat] LT",
                lastDay: "[dünən] LT",
                lastWeek: "[keçən həftə] dddd [saat] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s sonra",
                past: "%s əvvəl",
                s: "birneçə saniyyə",
                m: "bir dəqiqə",
                mm: "%d dəqiqə",
                h: "bir saat",
                hh: "%d saat",
                d: "bir gün",
                dd: "%d gün",
                M: "bir ay",
                MM: "%d ay",
                y: "bir il",
                yy: "%d il"
            },
            meridiemParse: /gecə|səhər|gündüz|axşam/,
            isPM: function (e) {
                return /^(gündüz|axşam)$/.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "gecə" : e < 12 ? "səhər" : e < 17 ? "gündüz" : "axşam"
            },
            ordinalParse: /\d{1,2}-(ıncı|inci|nci|üncü|ncı|uncu)/,
            ordinal: function (e) {
                if (0 === e)return e + "-ıncı";
                var n = e % 10, a = e % 100 - n, i = e >= 100 ? 100 : null;
                return e + (t[n] || t[a] || t[i])
            },
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t) {
            var n = e.split("_");
            return t % 10 === 1 && t % 100 !== 11 ? n[0] : t % 10 >= 2 && t % 10 <= 4 && (t % 100 < 10 || t % 100 >= 20) ? n[1] : n[2]
        }

        function n(e, n, a) {
            var i = {
                mm: n ? "хвіліна_хвіліны_хвілін" : "хвіліну_хвіліны_хвілін",
                hh: n ? "гадзіна_гадзіны_гадзін" : "гадзіну_гадзіны_гадзін",
                dd: "дзень_дні_дзён",
                MM: "месяц_месяцы_месяцаў",
                yy: "год_гады_гадоў"
            };
            return "m" === a ? n ? "хвіліна" : "хвіліну" : "h" === a ? n ? "гадзіна" : "гадзіну" : e + " " + t(i[a], +e)
        }

        var a = e.defineLocale("be", {
            months: {
                format: "студзеня_лютага_сакавіка_красавіка_траўня_чэрвеня_ліпеня_жніўня_верасня_кастрычніка_лістапада_снежня".split("_"),
                standalone: "студзень_люты_сакавік_красавік_травень_чэрвень_ліпень_жнівень_верасень_кастрычнік_лістапад_снежань".split("_")
            },
            monthsShort: "студ_лют_сак_крас_трав_чэрв_ліп_жнів_вер_каст_ліст_снеж".split("_"),
            weekdays: {
                format: "нядзелю_панядзелак_аўторак_сераду_чацвер_пятніцу_суботу".split("_"),
                standalone: "нядзеля_панядзелак_аўторак_серада_чацвер_пятніца_субота".split("_"),
                isFormat: /\[ ?[Вв] ?(?:мінулую|наступную)? ?\] ?dddd/
            },
            weekdaysShort: "нд_пн_ат_ср_чц_пт_сб".split("_"),
            weekdaysMin: "нд_пн_ат_ср_чц_пт_сб".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY г.",
                LLL: "D MMMM YYYY г., HH:mm",
                LLLL: "dddd, D MMMM YYYY г., HH:mm"
            },
            calendar: {
                sameDay: "[Сёння ў] LT",
                nextDay: "[Заўтра ў] LT",
                lastDay: "[Учора ў] LT",
                nextWeek: function () {
                    return "[У] dddd [ў] LT"
                },
                lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                        case 3:
                        case 5:
                        case 6:
                            return "[У мінулую] dddd [ў] LT";
                        case 1:
                        case 2:
                        case 4:
                            return "[У мінулы] dddd [ў] LT"
                    }
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "праз %s",
                past: "%s таму",
                s: "некалькі секунд",
                m: n,
                mm: n,
                h: n,
                hh: n,
                d: "дзень",
                dd: n,
                M: "месяц",
                MM: n,
                y: "год",
                yy: n
            },
            meridiemParse: /ночы|раніцы|дня|вечара/,
            isPM: function (e) {
                return /^(дня|вечара)$/.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "ночы" : e < 12 ? "раніцы" : e < 17 ? "дня" : "вечара"
            },
            ordinalParse: /\d{1,2}-(і|ы|га)/,
            ordinal: function (e, t) {
                switch (t) {
                    case"M":
                    case"d":
                    case"DDD":
                    case"w":
                    case"W":
                        return e % 10 !== 2 && e % 10 !== 3 || e % 100 === 12 || e % 100 === 13 ? e + "-ы" : e + "-і";
                    case"D":
                        return e + "-га";
                    default:
                        return e
                }
            },
            week: {dow: 1, doy: 7}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("bg", {
            months: "януари_февруари_март_април_май_юни_юли_август_септември_октомври_ноември_декември".split("_"),
            monthsShort: "янр_фев_мар_апр_май_юни_юли_авг_сеп_окт_ное_дек".split("_"),
            weekdays: "неделя_понеделник_вторник_сряда_четвъртък_петък_събота".split("_"),
            weekdaysShort: "нед_пон_вто_сря_чет_пет_съб".split("_"),
            weekdaysMin: "нд_пн_вт_ср_чт_пт_сб".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "D.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY H:mm",
                LLLL: "dddd, D MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[Днес в] LT",
                nextDay: "[Утре в] LT",
                nextWeek: "dddd [в] LT",
                lastDay: "[Вчера в] LT",
                lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                        case 3:
                        case 6:
                            return "[В изминалата] dddd [в] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[В изминалия] dddd [в] LT"
                    }
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "след %s",
                past: "преди %s",
                s: "няколко секунди",
                m: "минута",
                mm: "%d минути",
                h: "час",
                hh: "%d часа",
                d: "ден",
                dd: "%d дни",
                M: "месец",
                MM: "%d месеца",
                y: "година",
                yy: "%d години"
            },
            ordinalParse: /\d{1,2}-(ев|ен|ти|ви|ри|ми)/,
            ordinal: function (e) {
                var t = e % 10, n = e % 100;
                return 0 === e ? e + "-ев" : 0 === n ? e + "-ен" : n > 10 && n < 20 ? e + "-ти" : 1 === t ? e + "-ви" : 2 === t ? e + "-ри" : 7 === t || 8 === t ? e + "-ми" : e + "-ти"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "১", 2: "২", 3: "৩", 4: "৪", 5: "৫", 6: "৬", 7: "৭", 8: "৮", 9: "৯", 0: "০"}, n = {
            "১": "1",
            "২": "2",
            "৩": "3",
            "৪": "4",
            "৫": "5",
            "৬": "6",
            "৭": "7",
            "৮": "8",
            "৯": "9",
            "০": "0"
        }, a = e.defineLocale("bn", {
            months: "জানুয়ারী_ফেব্রুয়ারি_মার্চ_এপ্রিল_মে_জুন_জুলাই_আগস্ট_সেপ্টেম্বর_অক্টোবর_নভেম্বর_ডিসেম্বর".split("_"),
            monthsShort: "জানু_ফেব_মার্চ_এপ্র_মে_জুন_জুল_আগ_সেপ্ট_অক্টো_নভে_ডিসে".split("_"),
            weekdays: "রবিবার_সোমবার_মঙ্গলবার_বুধবার_বৃহস্পতিবার_শুক্রবার_শনিবার".split("_"),
            weekdaysShort: "রবি_সোম_মঙ্গল_বুধ_বৃহস্পতি_শুক্র_শনি".split("_"),
            weekdaysMin: "রবি_সোম_মঙ্গ_বুধ_বৃহঃ_শুক্র_শনি".split("_"),
            longDateFormat: {
                LT: "A h:mm সময়",
                LTS: "A h:mm:ss সময়",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, A h:mm সময়",
                LLLL: "dddd, D MMMM YYYY, A h:mm সময়"
            },
            calendar: {
                sameDay: "[আজ] LT",
                nextDay: "[আগামীকাল] LT",
                nextWeek: "dddd, LT",
                lastDay: "[গতকাল] LT",
                lastWeek: "[গত] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s পরে",
                past: "%s আগে",
                s: "কয়েক সেকেন্ড",
                m: "এক মিনিট",
                mm: "%d মিনিট",
                h: "এক ঘন্টা",
                hh: "%d ঘন্টা",
                d: "এক দিন",
                dd: "%d দিন",
                M: "এক মাস",
                MM: "%d মাস",
                y: "এক বছর",
                yy: "%d বছর"
            },
            preparse: function (e) {
                return e.replace(/[১২৩৪৫৬৭৮৯০]/g, function (e) {
                    return n[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                })
            },
            meridiemParse: /রাত|সকাল|দুপুর|বিকাল|রাত/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "রাত" === t && e >= 4 || "দুপুর" === t && e < 5 || "বিকাল" === t ? e + 12 : e
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "রাত" : e < 10 ? "সকাল" : e < 17 ? "দুপুর" : e < 20 ? "বিকাল" : "রাত"
            },
            week: {dow: 0, doy: 6}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "༡", 2: "༢", 3: "༣", 4: "༤", 5: "༥", 6: "༦", 7: "༧", 8: "༨", 9: "༩", 0: "༠"}, n = {
            "༡": "1",
            "༢": "2",
            "༣": "3",
            "༤": "4",
            "༥": "5",
            "༦": "6",
            "༧": "7",
            "༨": "8",
            "༩": "9",
            "༠": "0"
        }, a = e.defineLocale("bo", {
            months: "ཟླ་བ་དང་པོ_ཟླ་བ་གཉིས་པ_ཟླ་བ་གསུམ་པ_ཟླ་བ་བཞི་པ_ཟླ་བ་ལྔ་པ_ཟླ་བ་དྲུག་པ_ཟླ་བ་བདུན་པ_ཟླ་བ་བརྒྱད་པ_ཟླ་བ་དགུ་པ_ཟླ་བ་བཅུ་པ_ཟླ་བ་བཅུ་གཅིག་པ_ཟླ་བ་བཅུ་གཉིས་པ".split("_"),
            monthsShort: "ཟླ་བ་དང་པོ_ཟླ་བ་གཉིས་པ_ཟླ་བ་གསུམ་པ_ཟླ་བ་བཞི་པ_ཟླ་བ་ལྔ་པ_ཟླ་བ་དྲུག་པ_ཟླ་བ་བདུན་པ_ཟླ་བ་བརྒྱད་པ_ཟླ་བ་དགུ་པ_ཟླ་བ་བཅུ་པ_ཟླ་བ་བཅུ་གཅིག་པ_ཟླ་བ་བཅུ་གཉིས་པ".split("_"),
            weekdays: "གཟའ་ཉི་མ་_གཟའ་ཟླ་བ་_གཟའ་མིག་དམར་_གཟའ་ལྷག་པ་_གཟའ་ཕུར་བུ_གཟའ་པ་སངས་_གཟའ་སྤེན་པ་".split("_"),
            weekdaysShort: "ཉི་མ་_ཟླ་བ་_མིག་དམར་_ལྷག་པ་_ཕུར་བུ_པ་སངས་_སྤེན་པ་".split("_"),
            weekdaysMin: "ཉི་མ་_ཟླ་བ་_མིག་དམར་_ལྷག་པ་_ཕུར་བུ_པ་སངས་_སྤེན་པ་".split("_"),
            longDateFormat: {
                LT: "A h:mm",
                LTS: "A h:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, A h:mm",
                LLLL: "dddd, D MMMM YYYY, A h:mm"
            },
            calendar: {
                sameDay: "[དི་རིང] LT",
                nextDay: "[སང་ཉིན] LT",
                nextWeek: "[བདུན་ཕྲག་རྗེས་མ], LT",
                lastDay: "[ཁ་སང] LT",
                lastWeek: "[བདུན་ཕྲག་མཐའ་མ] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s ལ་",
                past: "%s སྔན་ལ",
                s: "ལམ་སང",
                m: "སྐར་མ་གཅིག",
                mm: "%d སྐར་མ",
                h: "ཆུ་ཚོད་གཅིག",
                hh: "%d ཆུ་ཚོད",
                d: "ཉིན་གཅིག",
                dd: "%d ཉིན་",
                M: "ཟླ་བ་གཅིག",
                MM: "%d ཟླ་བ",
                y: "ལོ་གཅིག",
                yy: "%d ལོ"
            },
            preparse: function (e) {
                return e.replace(/[༡༢༣༤༥༦༧༨༩༠]/g, function (e) {
                    return n[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                })
            },
            meridiemParse: /མཚན་མོ|ཞོགས་ཀས|ཉིན་གུང|དགོང་དག|མཚན་མོ/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "མཚན་མོ" === t && e >= 4 || "ཉིན་གུང" === t && e < 5 || "དགོང་དག" === t ? e + 12 : e
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "མཚན་མོ" : e < 10 ? "ཞོགས་ཀས" : e < 17 ? "ཉིན་གུང" : e < 20 ? "དགོང་དག" : "མཚན་མོ"
            },
            week: {dow: 0, doy: 6}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n) {
            var a = {mm: "munutenn", MM: "miz", dd: "devezh"};
            return e + " " + i(a[n], e)
        }

        function n(e) {
            switch (a(e)) {
                case 1:
                case 3:
                case 4:
                case 5:
                case 9:
                    return e + " bloaz";
                default:
                    return e + " vloaz"
            }
        }

        function a(e) {
            return e > 9 ? a(e % 10) : e
        }

        function i(e, t) {
            return 2 === t ? o(e) : e
        }

        function o(e) {
            var t = {m: "v", b: "v", d: "z"};
            return void 0 === t[e.charAt(0)] ? e : t[e.charAt(0)] + e.substring(1)
        }

        var r = e.defineLocale("br", {
            months: "Genver_C'hwevrer_Meurzh_Ebrel_Mae_Mezheven_Gouere_Eost_Gwengolo_Here_Du_Kerzu".split("_"),
            monthsShort: "Gen_C'hwe_Meu_Ebr_Mae_Eve_Gou_Eos_Gwe_Her_Du_Ker".split("_"),
            weekdays: "Sul_Lun_Meurzh_Merc'her_Yaou_Gwener_Sadorn".split("_"),
            weekdaysShort: "Sul_Lun_Meu_Mer_Yao_Gwe_Sad".split("_"),
            weekdaysMin: "Su_Lu_Me_Mer_Ya_Gw_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "h[e]mm A",
                LTS: "h[e]mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D [a viz] MMMM YYYY",
                LLL: "D [a viz] MMMM YYYY h[e]mm A",
                LLLL: "dddd, D [a viz] MMMM YYYY h[e]mm A"
            },
            calendar: {
                sameDay: "[Hiziv da] LT",
                nextDay: "[Warc'hoazh da] LT",
                nextWeek: "dddd [da] LT",
                lastDay: "[Dec'h da] LT",
                lastWeek: "dddd [paset da] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "a-benn %s",
                past: "%s 'zo",
                s: "un nebeud segondennoù",
                m: "ur vunutenn",
                mm: t,
                h: "un eur",
                hh: "%d eur",
                d: "un devezh",
                dd: t,
                M: "ur miz",
                MM: t,
                y: "ur bloaz",
                yy: n
            },
            ordinalParse: /\d{1,2}(añ|vet)/,
            ordinal: function (e) {
                var t = 1 === e ? "añ" : "vet";
                return e + t
            },
            week: {dow: 1, doy: 4}
        });
        return r
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n) {
            var a = e + " ";
            switch (n) {
                case"m":
                    return t ? "jedna minuta" : "jedne minute";
                case"mm":
                    return a += 1 === e ? "minuta" : 2 === e || 3 === e || 4 === e ? "minute" : "minuta";
                case"h":
                    return t ? "jedan sat" : "jednog sata";
                case"hh":
                    return a += 1 === e ? "sat" : 2 === e || 3 === e || 4 === e ? "sata" : "sati";
                case"dd":
                    return a += 1 === e ? "dan" : "dana";
                case"MM":
                    return a += 1 === e ? "mjesec" : 2 === e || 3 === e || 4 === e ? "mjeseca" : "mjeseci";
                case"yy":
                    return a += 1 === e ? "godina" : 2 === e || 3 === e || 4 === e ? "godine" : "godina"
            }
        }

        var n = e.defineLocale("bs", {
            months: "januar_februar_mart_april_maj_juni_juli_august_septembar_oktobar_novembar_decembar".split("_"),
            monthsShort: "jan._feb._mar._apr._maj._jun._jul._aug._sep._okt._nov._dec.".split("_"),
            monthsParseExact: !0,
            weekdays: "nedjelja_ponedjeljak_utorak_srijeda_četvrtak_petak_subota".split("_"),
            weekdaysShort: "ned._pon._uto._sri._čet._pet._sub.".split("_"),
            weekdaysMin: "ne_po_ut_sr_če_pe_su".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd, D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[danas u] LT", nextDay: "[sutra u] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[u] [nedjelju] [u] LT";
                        case 3:
                            return "[u] [srijedu] [u] LT";
                        case 6:
                            return "[u] [subotu] [u] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[u] dddd [u] LT"
                    }
                }, lastDay: "[jučer u] LT", lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                        case 3:
                            return "[prošlu] dddd [u] LT";
                        case 6:
                            return "[prošle] [subote] [u] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[prošli] dddd [u] LT"
                    }
                }, sameElse: "L"
            },
            relativeTime: {
                future: "za %s",
                past: "prije %s",
                s: "par sekundi",
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: "dan",
                dd: t,
                M: "mjesec",
                MM: t,
                y: "godinu",
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ca", {
            months: "gener_febrer_març_abril_maig_juny_juliol_agost_setembre_octubre_novembre_desembre".split("_"),
            monthsShort: "gen._febr._mar._abr._mai._jun._jul._ag._set._oct._nov._des.".split("_"),
            monthsParseExact: !0,
            weekdays: "diumenge_dilluns_dimarts_dimecres_dijous_divendres_dissabte".split("_"),
            weekdaysShort: "dg._dl._dt._dc._dj._dv._ds.".split("_"),
            weekdaysMin: "Dg_Dl_Dt_Dc_Dj_Dv_Ds".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY H:mm",
                LLLL: "dddd D MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: function () {
                    return "[avui a " + (1 !== this.hours() ? "les" : "la") + "] LT"
                }, nextDay: function () {
                    return "[demà a " + (1 !== this.hours() ? "les" : "la") + "] LT"
                }, nextWeek: function () {
                    return "dddd [a " + (1 !== this.hours() ? "les" : "la") + "] LT"
                }, lastDay: function () {
                    return "[ahir a " + (1 !== this.hours() ? "les" : "la") + "] LT"
                }, lastWeek: function () {
                    return "[el] dddd [passat a " + (1 !== this.hours() ? "les" : "la") + "] LT"
                }, sameElse: "L"
            },
            relativeTime: {
                future: "d'aquí %s",
                past: "fa %s",
                s: "uns segons",
                m: "un minut",
                mm: "%d minuts",
                h: "una hora",
                hh: "%d hores",
                d: "un dia",
                dd: "%d dies",
                M: "un mes",
                MM: "%d mesos",
                y: "un any",
                yy: "%d anys"
            },
            ordinalParse: /\d{1,2}(r|n|t|è|a)/,
            ordinal: function (e, t) {
                var n = 1 === e ? "r" : 2 === e ? "n" : 3 === e ? "r" : 4 === e ? "t" : "è";
                return "w" !== t && "W" !== t || (n = "a"), e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e) {
            return e > 1 && e < 5 && 1 !== ~~(e / 10)
        }

        function n(e, n, a, i) {
            var o = e + " ";
            switch (a) {
                case"s":
                    return n || i ? "pár sekund" : "pár sekundami";
                case"m":
                    return n ? "minuta" : i ? "minutu" : "minutou";
                case"mm":
                    return n || i ? o + (t(e) ? "minuty" : "minut") : o + "minutami";
                case"h":
                    return n ? "hodina" : i ? "hodinu" : "hodinou";
                case"hh":
                    return n || i ? o + (t(e) ? "hodiny" : "hodin") : o + "hodinami";
                case"d":
                    return n || i ? "den" : "dnem";
                case"dd":
                    return n || i ? o + (t(e) ? "dny" : "dní") : o + "dny";
                case"M":
                    return n || i ? "měsíc" : "měsícem";
                case"MM":
                    return n || i ? o + (t(e) ? "měsíce" : "měsíců") : o + "měsíci";
                case"y":
                    return n || i ? "rok" : "rokem";
                case"yy":
                    return n || i ? o + (t(e) ? "roky" : "let") : o + "lety"
            }
        }

        var a = "leden_únor_březen_duben_květen_červen_červenec_srpen_září_říjen_listopad_prosinec".split("_"), i = "led_úno_bře_dub_kvě_čvn_čvc_srp_zář_říj_lis_pro".split("_"), o = e.defineLocale("cs", {
            months: a,
            monthsShort: i,
            monthsParse: function (e, t) {
                var n, a = [];
                for (n = 0; n < 12; n++)a[n] = new RegExp("^" + e[n] + "$|^" + t[n] + "$", "i");
                return a
            }(a, i),
            shortMonthsParse: function (e) {
                var t, n = [];
                for (t = 0; t < 12; t++)n[t] = new RegExp("^" + e[t] + "$", "i");
                return n
            }(i),
            longMonthsParse: function (e) {
                var t, n = [];
                for (t = 0; t < 12; t++)n[t] = new RegExp("^" + e[t] + "$", "i");
                return n
            }(a),
            weekdays: "neděle_pondělí_úterý_středa_čtvrtek_pátek_sobota".split("_"),
            weekdaysShort: "ne_po_út_st_čt_pá_so".split("_"),
            weekdaysMin: "ne_po_út_st_čt_pá_so".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd D. MMMM YYYY H:mm",
                l: "D. M. YYYY"
            },
            calendar: {
                sameDay: "[dnes v] LT", nextDay: "[zítra v] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[v neděli v] LT";
                        case 1:
                        case 2:
                            return "[v] dddd [v] LT";
                        case 3:
                            return "[ve středu v] LT";
                        case 4:
                            return "[ve čtvrtek v] LT";
                        case 5:
                            return "[v pátek v] LT";
                        case 6:
                            return "[v sobotu v] LT"
                    }
                }, lastDay: "[včera v] LT", lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[minulou neděli v] LT";
                        case 1:
                        case 2:
                            return "[minulé] dddd [v] LT";
                        case 3:
                            return "[minulou středu v] LT";
                        case 4:
                        case 5:
                            return "[minulý] dddd [v] LT";
                        case 6:
                            return "[minulou sobotu v] LT"
                    }
                }, sameElse: "L"
            },
            relativeTime: {
                future: "za %s",
                past: "před %s",
                s: n,
                m: n,
                mm: n,
                h: n,
                hh: n,
                d: n,
                dd: n,
                M: n,
                MM: n,
                y: n,
                yy: n
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("cv", {
            months: "кӑрлач_нарӑс_пуш_ака_май_ҫӗртме_утӑ_ҫурла_авӑн_юпа_чӳк_раштав".split("_"),
            monthsShort: "кӑр_нар_пуш_ака_май_ҫӗр_утӑ_ҫур_авн_юпа_чӳк_раш".split("_"),
            weekdays: "вырсарникун_тунтикун_ытларикун_юнкун_кӗҫнерникун_эрнекун_шӑматкун".split("_"),
            weekdaysShort: "выр_тун_ытл_юн_кӗҫ_эрн_шӑм".split("_"),
            weekdaysMin: "вр_тн_ыт_юн_кҫ_эр_шм".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD-MM-YYYY",
                LL: "YYYY [ҫулхи] MMMM [уйӑхӗн] D[-мӗшӗ]",
                LLL: "YYYY [ҫулхи] MMMM [уйӑхӗн] D[-мӗшӗ], HH:mm",
                LLLL: "dddd, YYYY [ҫулхи] MMMM [уйӑхӗн] D[-мӗшӗ], HH:mm"
            },
            calendar: {
                sameDay: "[Паян] LT [сехетре]",
                nextDay: "[Ыран] LT [сехетре]",
                lastDay: "[Ӗнер] LT [сехетре]",
                nextWeek: "[Ҫитес] dddd LT [сехетре]",
                lastWeek: "[Иртнӗ] dddd LT [сехетре]",
                sameElse: "L"
            },
            relativeTime: {
                future: function (e) {
                    var t = /сехет$/i.exec(e) ? "рен" : /ҫул$/i.exec(e) ? "тан" : "ран";
                    return e + t
                },
                past: "%s каялла",
                s: "пӗр-ик ҫеккунт",
                m: "пӗр минут",
                mm: "%d минут",
                h: "пӗр сехет",
                hh: "%d сехет",
                d: "пӗр кун",
                dd: "%d кун",
                M: "пӗр уйӑх",
                MM: "%d уйӑх",
                y: "пӗр ҫул",
                yy: "%d ҫул"
            },
            ordinalParse: /\d{1,2}-мӗш/,
            ordinal: "%d-мӗш",
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("cy", {
            months: "Ionawr_Chwefror_Mawrth_Ebrill_Mai_Mehefin_Gorffennaf_Awst_Medi_Hydref_Tachwedd_Rhagfyr".split("_"),
            monthsShort: "Ion_Chwe_Maw_Ebr_Mai_Meh_Gor_Aws_Med_Hyd_Tach_Rhag".split("_"),
            weekdays: "Dydd Sul_Dydd Llun_Dydd Mawrth_Dydd Mercher_Dydd Iau_Dydd Gwener_Dydd Sadwrn".split("_"),
            weekdaysShort: "Sul_Llun_Maw_Mer_Iau_Gwe_Sad".split("_"),
            weekdaysMin: "Su_Ll_Ma_Me_Ia_Gw_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Heddiw am] LT",
                nextDay: "[Yfory am] LT",
                nextWeek: "dddd [am] LT",
                lastDay: "[Ddoe am] LT",
                lastWeek: "dddd [diwethaf am] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "mewn %s",
                past: "%s yn ôl",
                s: "ychydig eiliadau",
                m: "munud",
                mm: "%d munud",
                h: "awr",
                hh: "%d awr",
                d: "diwrnod",
                dd: "%d diwrnod",
                M: "mis",
                MM: "%d mis",
                y: "blwyddyn",
                yy: "%d flynedd"
            },
            ordinalParse: /\d{1,2}(fed|ain|af|il|ydd|ed|eg)/,
            ordinal: function (e) {
                var t = e, n = "", a = ["", "af", "il", "ydd", "ydd", "ed", "ed", "ed", "fed", "fed", "fed", "eg", "fed", "eg", "eg", "fed", "eg", "eg", "fed", "eg", "fed"];
                return t > 20 ? n = 40 === t || 50 === t || 60 === t || 80 === t || 100 === t ? "fed" : "ain" : t > 0 && (n = a[t]), e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("da", {
            months: "januar_februar_marts_april_maj_juni_juli_august_september_oktober_november_december".split("_"),
            monthsShort: "jan_feb_mar_apr_maj_jun_jul_aug_sep_okt_nov_dec".split("_"),
            weekdays: "søndag_mandag_tirsdag_onsdag_torsdag_fredag_lørdag".split("_"),
            weekdaysShort: "søn_man_tir_ons_tor_fre_lør".split("_"),
            weekdaysMin: "sø_ma_ti_on_to_fr_lø".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY HH:mm",
                LLLL: "dddd [d.] D. MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[I dag kl.] LT",
                nextDay: "[I morgen kl.] LT",
                nextWeek: "dddd [kl.] LT",
                lastDay: "[I går kl.] LT",
                lastWeek: "[sidste] dddd [kl] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "om %s",
                past: "%s siden",
                s: "få sekunder",
                m: "et minut",
                mm: "%d minutter",
                h: "en time",
                hh: "%d timer",
                d: "en dag",
                dd: "%d dage",
                M: "en måned",
                MM: "%d måneder",
                y: "et år",
                yy: "%d år"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = {
                m: ["eine Minute", "einer Minute"],
                h: ["eine Stunde", "einer Stunde"],
                d: ["ein Tag", "einem Tag"],
                dd: [e + " Tage", e + " Tagen"],
                M: ["ein Monat", "einem Monat"],
                MM: [e + " Monate", e + " Monaten"],
                y: ["ein Jahr", "einem Jahr"],
                yy: [e + " Jahre", e + " Jahren"]
            };
            return t ? i[n][0] : i[n][1]
        }

        var n = e.defineLocale("de", {
            months: "Januar_Februar_März_April_Mai_Juni_Juli_August_September_Oktober_November_Dezember".split("_"),
            monthsShort: "Jan._Febr._Mrz._Apr._Mai_Jun._Jul._Aug._Sept._Okt._Nov._Dez.".split("_"),
            monthsParseExact: !0,
            weekdays: "Sonntag_Montag_Dienstag_Mittwoch_Donnerstag_Freitag_Samstag".split("_"),
            weekdaysShort: "So._Mo._Di._Mi._Do._Fr._Sa.".split("_"),
            weekdaysMin: "So_Mo_Di_Mi_Do_Fr_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY HH:mm",
                LLLL: "dddd, D. MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[heute um] LT [Uhr]",
                sameElse: "L",
                nextDay: "[morgen um] LT [Uhr]",
                nextWeek: "dddd [um] LT [Uhr]",
                lastDay: "[gestern um] LT [Uhr]",
                lastWeek: "[letzten] dddd [um] LT [Uhr]"
            },
            relativeTime: {
                future: "in %s",
                past: "vor %s",
                s: "ein paar Sekunden",
                m: t,
                mm: "%d Minuten",
                h: t,
                hh: "%d Stunden",
                d: t,
                dd: t,
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = {
                m: ["eine Minute", "einer Minute"],
                h: ["eine Stunde", "einer Stunde"],
                d: ["ein Tag", "einem Tag"],
                dd: [e + " Tage", e + " Tagen"],
                M: ["ein Monat", "einem Monat"],
                MM: [e + " Monate", e + " Monaten"],
                y: ["ein Jahr", "einem Jahr"],
                yy: [e + " Jahre", e + " Jahren"]
            };
            return t ? i[n][0] : i[n][1]
        }

        var n = e.defineLocale("de-at", {
            months: "Jänner_Februar_März_April_Mai_Juni_Juli_August_September_Oktober_November_Dezember".split("_"),
            monthsShort: "Jän._Febr._Mrz._Apr._Mai_Jun._Jul._Aug._Sept._Okt._Nov._Dez.".split("_"),
            monthsParseExact: !0,
            weekdays: "Sonntag_Montag_Dienstag_Mittwoch_Donnerstag_Freitag_Samstag".split("_"),
            weekdaysShort: "So._Mo._Di._Mi._Do._Fr._Sa.".split("_"),
            weekdaysMin: "So_Mo_Di_Mi_Do_Fr_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY HH:mm",
                LLLL: "dddd, D. MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[heute um] LT [Uhr]",
                sameElse: "L",
                nextDay: "[morgen um] LT [Uhr]",
                nextWeek: "dddd [um] LT [Uhr]",
                lastDay: "[gestern um] LT [Uhr]",
                lastWeek: "[letzten] dddd [um] LT [Uhr]"
            },
            relativeTime: {
                future: "in %s",
                past: "vor %s",
                s: "ein paar Sekunden",
                m: t,
                mm: "%d Minuten",
                h: t,
                hh: "%d Stunden",
                d: t,
                dd: t,
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = ["ޖެނުއަރީ", "ފެބްރުއަރީ", "މާރިޗު", "އޭޕްރީލު", "މޭ", "ޖޫން", "ޖުލައި", "އޯގަސްޓު", "ސެޕްޓެމްބަރު", "އޮކްޓޯބަރު", "ނޮވެމްބަރު", "ޑިސެމްބަރު"], n = ["އާދިއްތަ", "ހޯމަ", "އަންގާރަ", "ބުދަ", "ބުރާސްފަތި", "ހުކުރު", "ހޮނިހިރު"], a = e.defineLocale("dv", {
            months: t,
            monthsShort: t,
            weekdays: n,
            weekdaysShort: n,
            weekdaysMin: "އާދި_ހޯމަ_އަން_ބުދަ_ބުރާ_ހުކު_ހޮނި".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "D/M/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            meridiemParse: /މކ|މފ/,
            isPM: function (e) {
                return "މފ" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "މކ" : "މފ"
            },
            calendar: {
                sameDay: "[މިއަދު] LT",
                nextDay: "[މާދަމާ] LT",
                nextWeek: "dddd LT",
                lastDay: "[އިއްޔެ] LT",
                lastWeek: "[ފާއިތުވި] dddd LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "ތެރޭގައި %s",
                past: "ކުރިން %s",
                s: "ސިކުންތުކޮޅެއް",
                m: "މިނިޓެއް",
                mm: "މިނިޓު %d",
                h: "ގަޑިއިރެއް",
                hh: "ގަޑިއިރު %d",
                d: "ދުވަހެއް",
                dd: "ދުވަސް %d",
                M: "މަހެއް",
                MM: "މަސް %d",
                y: "އަހަރެއް",
                yy: "އަހަރު %d"
            },
            preparse: function (e) {
                return e.replace(/،/g, ",")
            },
            postformat: function (e) {
                return e.replace(/,/g, "،")
            },
            week: {dow: 7, doy: 12}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e) {
            return e instanceof Function || "[object Function]" === Object.prototype.toString.call(e)
        }

        var n = e.defineLocale("el", {
            monthsNominativeEl: "Ιανουάριος_Φεβρουάριος_Μάρτιος_Απρίλιος_Μάιος_Ιούνιος_Ιούλιος_Αύγουστος_Σεπτέμβριος_Οκτώβριος_Νοέμβριος_Δεκέμβριος".split("_"),
            monthsGenitiveEl: "Ιανουαρίου_Φεβρουαρίου_Μαρτίου_Απριλίου_Μαΐου_Ιουνίου_Ιουλίου_Αυγούστου_Σεπτεμβρίου_Οκτωβρίου_Νοεμβρίου_Δεκεμβρίου".split("_"),
            months: function (e, t) {
                return /D/.test(t.substring(0, t.indexOf("MMMM"))) ? this._monthsGenitiveEl[e.month()] : this._monthsNominativeEl[e.month()]
            },
            monthsShort: "Ιαν_Φεβ_Μαρ_Απρ_Μαϊ_Ιουν_Ιουλ_Αυγ_Σεπ_Οκτ_Νοε_Δεκ".split("_"),
            weekdays: "Κυριακή_Δευτέρα_Τρίτη_Τετάρτη_Πέμπτη_Παρασκευή_Σάββατο".split("_"),
            weekdaysShort: "Κυρ_Δευ_Τρι_Τετ_Πεμ_Παρ_Σαβ".split("_"),
            weekdaysMin: "Κυ_Δε_Τρ_Τε_Πε_Πα_Σα".split("_"),
            meridiem: function (e, t, n) {
                return e > 11 ? n ? "μμ" : "ΜΜ" : n ? "πμ" : "ΠΜ"
            },
            isPM: function (e) {
                return "μ" === (e + "").toLowerCase()[0]
            },
            meridiemParse: /[ΠΜ]\.?Μ?\.?/i,
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY h:mm A",
                LLLL: "dddd, D MMMM YYYY h:mm A"
            },
            calendarEl: {
                sameDay: "[Σήμερα {}] LT",
                nextDay: "[Αύριο {}] LT",
                nextWeek: "dddd [{}] LT",
                lastDay: "[Χθες {}] LT",
                lastWeek: function () {
                    switch (this.day()) {
                        case 6:
                            return "[το προηγούμενο] dddd [{}] LT";
                        default:
                            return "[την προηγούμενη] dddd [{}] LT"
                    }
                },
                sameElse: "L"
            },
            calendar: function (e, n) {
                var a = this._calendarEl[e], i = n && n.hours();
                return t(a) && (a = a.apply(n)), a.replace("{}", i % 12 === 1 ? "στη" : "στις")
            },
            relativeTime: {
                future: "σε %s",
                past: "%s πριν",
                s: "λίγα δευτερόλεπτα",
                m: "ένα λεπτό",
                mm: "%d λεπτά",
                h: "μία ώρα",
                hh: "%d ώρες",
                d: "μία μέρα",
                dd: "%d μέρες",
                M: "ένας μήνας",
                MM: "%d μήνες",
                y: "ένας χρόνος",
                yy: "%d χρόνια"
            },
            ordinalParse: /\d{1,2}η/,
            ordinal: "%dη",
            week: {dow: 1, doy: 4}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("en-au", {
            months: "January_February_March_April_May_June_July_August_September_October_November_December".split("_"),
            monthsShort: "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),
            weekdays: "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),
            weekdaysShort: "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),
            weekdaysMin: "Su_Mo_Tu_We_Th_Fr_Sa".split("_"),
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY h:mm A",
                LLLL: "dddd, D MMMM YYYY h:mm A"
            },
            calendar: {
                sameDay: "[Today at] LT",
                nextDay: "[Tomorrow at] LT",
                nextWeek: "dddd [at] LT",
                lastDay: "[Yesterday at] LT",
                lastWeek: "[Last] dddd [at] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "in %s",
                past: "%s ago",
                s: "a few seconds",
                m: "a minute",
                mm: "%d minutes",
                h: "an hour",
                hh: "%d hours",
                d: "a day",
                dd: "%d days",
                M: "a month",
                MM: "%d months",
                y: "a year",
                yy: "%d years"
            },
            ordinalParse: /\d{1,2}(st|nd|rd|th)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                return e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("en-ca", {
            months: "January_February_March_April_May_June_July_August_September_October_November_December".split("_"),
            monthsShort: "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),
            weekdays: "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),
            weekdaysShort: "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),
            weekdaysMin: "Su_Mo_Tu_We_Th_Fr_Sa".split("_"),
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "YYYY-MM-DD",
                LL: "MMMM D, YYYY",
                LLL: "MMMM D, YYYY h:mm A",
                LLLL: "dddd, MMMM D, YYYY h:mm A"
            },
            calendar: {
                sameDay: "[Today at] LT",
                nextDay: "[Tomorrow at] LT",
                nextWeek: "dddd [at] LT",
                lastDay: "[Yesterday at] LT",
                lastWeek: "[Last] dddd [at] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "in %s",
                past: "%s ago",
                s: "a few seconds",
                m: "a minute",
                mm: "%d minutes",
                h: "an hour",
                hh: "%d hours",
                d: "a day",
                dd: "%d days",
                M: "a month",
                MM: "%d months",
                y: "a year",
                yy: "%d years"
            },
            ordinalParse: /\d{1,2}(st|nd|rd|th)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                return e + n
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("en-gb", {
            months: "January_February_March_April_May_June_July_August_September_October_November_December".split("_"),
            monthsShort: "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),
            weekdays: "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),
            weekdaysShort: "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),
            weekdaysMin: "Su_Mo_Tu_We_Th_Fr_Sa".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Today at] LT",
                nextDay: "[Tomorrow at] LT",
                nextWeek: "dddd [at] LT",
                lastDay: "[Yesterday at] LT",
                lastWeek: "[Last] dddd [at] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "in %s",
                past: "%s ago",
                s: "a few seconds",
                m: "a minute",
                mm: "%d minutes",
                h: "an hour",
                hh: "%d hours",
                d: "a day",
                dd: "%d days",
                M: "a month",
                MM: "%d months",
                y: "a year",
                yy: "%d years"
            },
            ordinalParse: /\d{1,2}(st|nd|rd|th)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                return e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("en-ie", {
            months: "January_February_March_April_May_June_July_August_September_October_November_December".split("_"),
            monthsShort: "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),
            weekdays: "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),
            weekdaysShort: "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),
            weekdaysMin: "Su_Mo_Tu_We_Th_Fr_Sa".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD-MM-YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Today at] LT",
                nextDay: "[Tomorrow at] LT",
                nextWeek: "dddd [at] LT",
                lastDay: "[Yesterday at] LT",
                lastWeek: "[Last] dddd [at] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "in %s",
                past: "%s ago",
                s: "a few seconds",
                m: "a minute",
                mm: "%d minutes",
                h: "an hour",
                hh: "%d hours",
                d: "a day",
                dd: "%d days",
                M: "a month",
                MM: "%d months",
                y: "a year",
                yy: "%d years"
            },
            ordinalParse: /\d{1,2}(st|nd|rd|th)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                return e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("en-nz", {
            months: "January_February_March_April_May_June_July_August_September_October_November_December".split("_"),
            monthsShort: "Jan_Feb_Mar_Apr_May_Jun_Jul_Aug_Sep_Oct_Nov_Dec".split("_"),
            weekdays: "Sunday_Monday_Tuesday_Wednesday_Thursday_Friday_Saturday".split("_"),
            weekdaysShort: "Sun_Mon_Tue_Wed_Thu_Fri_Sat".split("_"),
            weekdaysMin: "Su_Mo_Tu_We_Th_Fr_Sa".split("_"),
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY h:mm A",
                LLLL: "dddd, D MMMM YYYY h:mm A"
            },
            calendar: {
                sameDay: "[Today at] LT",
                nextDay: "[Tomorrow at] LT",
                nextWeek: "dddd [at] LT",
                lastDay: "[Yesterday at] LT",
                lastWeek: "[Last] dddd [at] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "in %s",
                past: "%s ago",
                s: "a few seconds",
                m: "a minute",
                mm: "%d minutes",
                h: "an hour",
                hh: "%d hours",
                d: "a day",
                dd: "%d days",
                M: "a month",
                MM: "%d months",
                y: "a year",
                yy: "%d years"
            },
            ordinalParse: /\d{1,2}(st|nd|rd|th)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                return e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("eo", {
            months: "januaro_februaro_marto_aprilo_majo_junio_julio_aŭgusto_septembro_oktobro_novembro_decembro".split("_"),
            monthsShort: "jan_feb_mar_apr_maj_jun_jul_aŭg_sep_okt_nov_dec".split("_"),
            weekdays: "Dimanĉo_Lundo_Mardo_Merkredo_Ĵaŭdo_Vendredo_Sabato".split("_"),
            weekdaysShort: "Dim_Lun_Mard_Merk_Ĵaŭ_Ven_Sab".split("_"),
            weekdaysMin: "Di_Lu_Ma_Me_Ĵa_Ve_Sa".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "YYYY-MM-DD",
                LL: "D[-an de] MMMM, YYYY",
                LLL: "D[-an de] MMMM, YYYY HH:mm",
                LLLL: "dddd, [la] D[-an de] MMMM, YYYY HH:mm"
            },
            meridiemParse: /[ap]\.t\.m/i,
            isPM: function (e) {
                return "p" === e.charAt(0).toLowerCase()
            },
            meridiem: function (e, t, n) {
                return e > 11 ? n ? "p.t.m." : "P.T.M." : n ? "a.t.m." : "A.T.M."
            },
            calendar: {
                sameDay: "[Hodiaŭ je] LT",
                nextDay: "[Morgaŭ je] LT",
                nextWeek: "dddd [je] LT",
                lastDay: "[Hieraŭ je] LT",
                lastWeek: "[pasinta] dddd [je] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "je %s",
                past: "antaŭ %s",
                s: "sekundoj",
                m: "minuto",
                mm: "%d minutoj",
                h: "horo",
                hh: "%d horoj",
                d: "tago",
                dd: "%d tagoj",
                M: "monato",
                MM: "%d monatoj",
                y: "jaro",
                yy: "%d jaroj"
            },
            ordinalParse: /\d{1,2}a/,
            ordinal: "%da",
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = "ene._feb._mar._abr._may._jun._jul._ago._sep._oct._nov._dic.".split("_"), n = "ene_feb_mar_abr_may_jun_jul_ago_sep_oct_nov_dic".split("_"), a = e.defineLocale("es", {
            months: "enero_febrero_marzo_abril_mayo_junio_julio_agosto_septiembre_octubre_noviembre_diciembre".split("_"),
            monthsShort: function (e, a) {
                return /-MMM-/.test(a) ? n[e.month()] : t[e.month()]
            },
            monthsParseExact: !0,
            weekdays: "domingo_lunes_martes_miércoles_jueves_viernes_sábado".split("_"),
            weekdaysShort: "dom._lun._mar._mié._jue._vie._sáb.".split("_"),
            weekdaysMin: "do_lu_ma_mi_ju_vi_sá".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D [de] MMMM [de] YYYY",
                LLL: "D [de] MMMM [de] YYYY H:mm",
                LLLL: "dddd, D [de] MMMM [de] YYYY H:mm"
            },
            calendar: {
                sameDay: function () {
                    return "[hoy a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, nextDay: function () {
                    return "[mañana a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, nextWeek: function () {
                    return "dddd [a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, lastDay: function () {
                    return "[ayer a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, lastWeek: function () {
                    return "[el] dddd [pasado a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, sameElse: "L"
            },
            relativeTime: {
                future: "en %s",
                past: "hace %s",
                s: "unos segundos",
                m: "un minuto",
                mm: "%d minutos",
                h: "una hora",
                hh: "%d horas",
                d: "un día",
                dd: "%d días",
                M: "un mes",
                MM: "%d meses",
                y: "un año",
                yy: "%d años"
            },
            ordinalParse: /\d{1,2}º/,
            ordinal: "%dº",
            week: {dow: 1, doy: 4}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = "ene._feb._mar._abr._may._jun._jul._ago._sep._oct._nov._dic.".split("_"), n = "ene_feb_mar_abr_may_jun_jul_ago_sep_oct_nov_dic".split("_"), a = e.defineLocale("es-do", {
            months: "enero_febrero_marzo_abril_mayo_junio_julio_agosto_septiembre_octubre_noviembre_diciembre".split("_"),
            monthsShort: function (e, a) {
                return /-MMM-/.test(a) ? n[e.month()] : t[e.month()]
            },
            monthsParseExact: !0,
            weekdays: "domingo_lunes_martes_miércoles_jueves_viernes_sábado".split("_"),
            weekdaysShort: "dom._lun._mar._mié._jue._vie._sáb.".split("_"),
            weekdaysMin: "do_lu_ma_mi_ju_vi_sá".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D [de] MMMM [de] YYYY",
                LLL: "D [de] MMMM [de] YYYY h:mm A",
                LLLL: "dddd, D [de] MMMM [de] YYYY h:mm A"
            },
            calendar: {
                sameDay: function () {
                    return "[hoy a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, nextDay: function () {
                    return "[mañana a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, nextWeek: function () {
                    return "dddd [a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, lastDay: function () {
                    return "[ayer a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, lastWeek: function () {
                    return "[el] dddd [pasado a la" + (1 !== this.hours() ? "s" : "") + "] LT"
                }, sameElse: "L"
            },
            relativeTime: {
                future: "en %s",
                past: "hace %s",
                s: "unos segundos",
                m: "un minuto",
                mm: "%d minutos",
                h: "una hora",
                hh: "%d horas",
                d: "un día",
                dd: "%d días",
                M: "un mes",
                MM: "%d meses",
                y: "un año",
                yy: "%d años"
            },
            ordinalParse: /\d{1,2}º/,
            ordinal: "%dº",
            week: {dow: 1, doy: 4}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = {
                s: ["mõne sekundi", "mõni sekund", "paar sekundit"],
                m: ["ühe minuti", "üks minut"],
                mm: [e + " minuti", e + " minutit"],
                h: ["ühe tunni", "tund aega", "üks tund"],
                hh: [e + " tunni", e + " tundi"],
                d: ["ühe päeva", "üks päev"],
                M: ["kuu aja", "kuu aega", "üks kuu"],
                MM: [e + " kuu", e + " kuud"],
                y: ["ühe aasta", "aasta", "üks aasta"],
                yy: [e + " aasta", e + " aastat"]
            };
            return t ? i[n][2] ? i[n][2] : i[n][1] : a ? i[n][0] : i[n][1]
        }

        var n = e.defineLocale("et", {
            months: "jaanuar_veebruar_märts_aprill_mai_juuni_juuli_august_september_oktoober_november_detsember".split("_"),
            monthsShort: "jaan_veebr_märts_apr_mai_juuni_juuli_aug_sept_okt_nov_dets".split("_"),
            weekdays: "pühapäev_esmaspäev_teisipäev_kolmapäev_neljapäev_reede_laupäev".split("_"),
            weekdaysShort: "P_E_T_K_N_R_L".split("_"),
            weekdaysMin: "P_E_T_K_N_R_L".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd, D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[Täna,] LT",
                nextDay: "[Homme,] LT",
                nextWeek: "[Järgmine] dddd LT",
                lastDay: "[Eile,] LT",
                lastWeek: "[Eelmine] dddd LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s pärast",
                past: "%s tagasi",
                s: t,
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: t,
                dd: "%d päeva",
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("eu", {
            months: "urtarrila_otsaila_martxoa_apirila_maiatza_ekaina_uztaila_abuztua_iraila_urria_azaroa_abendua".split("_"),
            monthsShort: "urt._ots._mar._api._mai._eka._uzt._abu._ira._urr._aza._abe.".split("_"),
            monthsParseExact: !0,
            weekdays: "igandea_astelehena_asteartea_asteazkena_osteguna_ostirala_larunbata".split("_"),
            weekdaysShort: "ig._al._ar._az._og._ol._lr.".split("_"),
            weekdaysMin: "ig_al_ar_az_og_ol_lr".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "YYYY-MM-DD",
                LL: "YYYY[ko] MMMM[ren] D[a]",
                LLL: "YYYY[ko] MMMM[ren] D[a] HH:mm",
                LLLL: "dddd, YYYY[ko] MMMM[ren] D[a] HH:mm",
                l: "YYYY-M-D",
                ll: "YYYY[ko] MMM D[a]",
                lll: "YYYY[ko] MMM D[a] HH:mm",
                llll: "ddd, YYYY[ko] MMM D[a] HH:mm"
            },
            calendar: {
                sameDay: "[gaur] LT[etan]",
                nextDay: "[bihar] LT[etan]",
                nextWeek: "dddd LT[etan]",
                lastDay: "[atzo] LT[etan]",
                lastWeek: "[aurreko] dddd LT[etan]",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s barru",
                past: "duela %s",
                s: "segundo batzuk",
                m: "minutu bat",
                mm: "%d minutu",
                h: "ordu bat",
                hh: "%d ordu",
                d: "egun bat",
                dd: "%d egun",
                M: "hilabete bat",
                MM: "%d hilabete",
                y: "urte bat",
                yy: "%d urte"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "۱", 2: "۲", 3: "۳", 4: "۴", 5: "۵", 6: "۶", 7: "۷", 8: "۸", 9: "۹", 0: "۰"}, n = {
            "۱": "1",
            "۲": "2",
            "۳": "3",
            "۴": "4",
            "۵": "5",
            "۶": "6",
            "۷": "7",
            "۸": "8",
            "۹": "9",
            "۰": "0"
        }, a = e.defineLocale("fa", {
            months: "ژانویه_فوریه_مارس_آوریل_مه_ژوئن_ژوئیه_اوت_سپتامبر_اکتبر_نوامبر_دسامبر".split("_"),
            monthsShort: "ژانویه_فوریه_مارس_آوریل_مه_ژوئن_ژوئیه_اوت_سپتامبر_اکتبر_نوامبر_دسامبر".split("_"),
            weekdays: "یک‌شنبه_دوشنبه_سه‌شنبه_چهارشنبه_پنج‌شنبه_جمعه_شنبه".split("_"),
            weekdaysShort: "یک‌شنبه_دوشنبه_سه‌شنبه_چهارشنبه_پنج‌شنبه_جمعه_شنبه".split("_"),
            weekdaysMin: "ی_د_س_چ_پ_ج_ش".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            meridiemParse: /قبل از ظهر|بعد از ظهر/,
            isPM: function (e) {
                return /بعد از ظهر/.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "قبل از ظهر" : "بعد از ظهر"
            },
            calendar: {
                sameDay: "[امروز ساعت] LT",
                nextDay: "[فردا ساعت] LT",
                nextWeek: "dddd [ساعت] LT",
                lastDay: "[دیروز ساعت] LT",
                lastWeek: "dddd [پیش] [ساعت] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "در %s",
                past: "%s پیش",
                s: "چندین ثانیه",
                m: "یک دقیقه",
                mm: "%d دقیقه",
                h: "یک ساعت",
                hh: "%d ساعت",
                d: "یک روز",
                dd: "%d روز",
                M: "یک ماه",
                MM: "%d ماه",
                y: "یک سال",
                yy: "%d سال"
            },
            preparse: function (e) {
                return e.replace(/[۰-۹]/g, function (e) {
                    return n[e]
                }).replace(/،/g, ",")
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                }).replace(/,/g, "،")
            },
            ordinalParse: /\d{1,2}م/,
            ordinal: "%dم",
            week: {dow: 6, doy: 12}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, a, i) {
            var o = "";
            switch (a) {
                case"s":
                    return i ? "muutaman sekunnin" : "muutama sekunti";
                case"m":
                    return i ? "minuutin" : "minuutti";
                case"mm":
                    o = i ? "minuutin" : "minuuttia";
                    break;
                case"h":
                    return i ? "tunnin" : "tunti";
                case"hh":
                    o = i ? "tunnin" : "tuntia";
                    break;
                case"d":
                    return i ? "päivän" : "päivä";
                case"dd":
                    o = i ? "päivän" : "päivää";
                    break;
                case"M":
                    return i ? "kuukauden" : "kuukausi";
                case"MM":
                    o = i ? "kuukauden" : "kuukautta";
                    break;
                case"y":
                    return i ? "vuoden" : "vuosi";
                case"yy":
                    o = i ? "vuoden" : "vuotta"
            }
            return o = n(e, i) + " " + o
        }

        function n(e, t) {
            return e < 10 ? t ? i[e] : a[e] : e
        }

        var a = "nolla yksi kaksi kolme neljä viisi kuusi seitsemän kahdeksan yhdeksän".split(" "), i = ["nolla", "yhden", "kahden", "kolmen", "neljän", "viiden", "kuuden", a[7], a[8], a[9]], o = e.defineLocale("fi", {
            months: "tammikuu_helmikuu_maaliskuu_huhtikuu_toukokuu_kesäkuu_heinäkuu_elokuu_syyskuu_lokakuu_marraskuu_joulukuu".split("_"),
            monthsShort: "tammi_helmi_maalis_huhti_touko_kesä_heinä_elo_syys_loka_marras_joulu".split("_"),
            weekdays: "sunnuntai_maanantai_tiistai_keskiviikko_torstai_perjantai_lauantai".split("_"),
            weekdaysShort: "su_ma_ti_ke_to_pe_la".split("_"),
            weekdaysMin: "su_ma_ti_ke_to_pe_la".split("_"),
            longDateFormat: {
                LT: "HH.mm",
                LTS: "HH.mm.ss",
                L: "DD.MM.YYYY",
                LL: "Do MMMM[ta] YYYY",
                LLL: "Do MMMM[ta] YYYY, [klo] HH.mm",
                LLLL: "dddd, Do MMMM[ta] YYYY, [klo] HH.mm",
                l: "D.M.YYYY",
                ll: "Do MMM YYYY",
                lll: "Do MMM YYYY, [klo] HH.mm",
                llll: "ddd, Do MMM YYYY, [klo] HH.mm"
            },
            calendar: {
                sameDay: "[tänään] [klo] LT",
                nextDay: "[huomenna] [klo] LT",
                nextWeek: "dddd [klo] LT",
                lastDay: "[eilen] [klo] LT",
                lastWeek: "[viime] dddd[na] [klo] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s päästä",
                past: "%s sitten",
                s: t,
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: t,
                dd: t,
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("fo", {
            months: "januar_februar_mars_apríl_mai_juni_juli_august_september_oktober_november_desember".split("_"),
            monthsShort: "jan_feb_mar_apr_mai_jun_jul_aug_sep_okt_nov_des".split("_"),
            weekdays: "sunnudagur_mánadagur_týsdagur_mikudagur_hósdagur_fríggjadagur_leygardagur".split("_"),
            weekdaysShort: "sun_mán_týs_mik_hós_frí_ley".split("_"),
            weekdaysMin: "su_má_tý_mi_hó_fr_le".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D. MMMM, YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Í dag kl.] LT",
                nextDay: "[Í morgin kl.] LT",
                nextWeek: "dddd [kl.] LT",
                lastDay: "[Í gjár kl.] LT",
                lastWeek: "[síðstu] dddd [kl] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "um %s",
                past: "%s síðani",
                s: "fá sekund",
                m: "ein minutt",
                mm: "%d minuttir",
                h: "ein tími",
                hh: "%d tímar",
                d: "ein dagur",
                dd: "%d dagar",
                M: "ein mánaði",
                MM: "%d mánaðir",
                y: "eitt ár",
                yy: "%d ár"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("fr", {
            months: "janvier_février_mars_avril_mai_juin_juillet_août_septembre_octobre_novembre_décembre".split("_"),
            monthsShort: "janv._févr._mars_avr._mai_juin_juil._août_sept._oct._nov._déc.".split("_"),
            monthsParseExact: !0,
            weekdays: "dimanche_lundi_mardi_mercredi_jeudi_vendredi_samedi".split("_"),
            weekdaysShort: "dim._lun._mar._mer._jeu._ven._sam.".split("_"),
            weekdaysMin: "Di_Lu_Ma_Me_Je_Ve_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Aujourd'hui à] LT",
                nextDay: "[Demain à] LT",
                nextWeek: "dddd [à] LT",
                lastDay: "[Hier à] LT",
                lastWeek: "dddd [dernier à] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "dans %s",
                past: "il y a %s",
                s: "quelques secondes",
                m: "une minute",
                mm: "%d minutes",
                h: "une heure",
                hh: "%d heures",
                d: "un jour",
                dd: "%d jours",
                M: "un mois",
                MM: "%d mois",
                y: "un an",
                yy: "%d ans"
            },
            ordinalParse: /\d{1,2}(er|)/,
            ordinal: function (e) {
                return e + (1 === e ? "er" : "")
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("fr-ca", {
            months: "janvier_février_mars_avril_mai_juin_juillet_août_septembre_octobre_novembre_décembre".split("_"),
            monthsShort: "janv._févr._mars_avr._mai_juin_juil._août_sept._oct._nov._déc.".split("_"),
            monthsParseExact: !0,
            weekdays: "dimanche_lundi_mardi_mercredi_jeudi_vendredi_samedi".split("_"),
            weekdaysShort: "dim._lun._mar._mer._jeu._ven._sam.".split("_"),
            weekdaysMin: "Di_Lu_Ma_Me_Je_Ve_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "YYYY-MM-DD",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Aujourd'hui à] LT",
                nextDay: "[Demain à] LT",
                nextWeek: "dddd [à] LT",
                lastDay: "[Hier à] LT",
                lastWeek: "dddd [dernier à] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "dans %s",
                past: "il y a %s",
                s: "quelques secondes",
                m: "une minute",
                mm: "%d minutes",
                h: "une heure",
                hh: "%d heures",
                d: "un jour",
                dd: "%d jours",
                M: "un mois",
                MM: "%d mois",
                y: "un an",
                yy: "%d ans"
            },
            ordinalParse: /\d{1,2}(er|e)/,
            ordinal: function (e) {
                return e + (1 === e ? "er" : "e")
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("fr-ch", {
            months: "janvier_février_mars_avril_mai_juin_juillet_août_septembre_octobre_novembre_décembre".split("_"),
            monthsShort: "janv._févr._mars_avr._mai_juin_juil._août_sept._oct._nov._déc.".split("_"),
            monthsParseExact: !0,
            weekdays: "dimanche_lundi_mardi_mercredi_jeudi_vendredi_samedi".split("_"),
            weekdaysShort: "dim._lun._mar._mer._jeu._ven._sam.".split("_"),
            weekdaysMin: "Di_Lu_Ma_Me_Je_Ve_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Aujourd'hui à] LT",
                nextDay: "[Demain à] LT",
                nextWeek: "dddd [à] LT",
                lastDay: "[Hier à] LT",
                lastWeek: "dddd [dernier à] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "dans %s",
                past: "il y a %s",
                s: "quelques secondes",
                m: "une minute",
                mm: "%d minutes",
                h: "une heure",
                hh: "%d heures",
                d: "un jour",
                dd: "%d jours",
                M: "un mois",
                MM: "%d mois",
                y: "un an",
                yy: "%d ans"
            },
            ordinalParse: /\d{1,2}(er|e)/,
            ordinal: function (e) {
                return e + (1 === e ? "er" : "e")
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = "jan._feb._mrt._apr._mai_jun._jul._aug._sep._okt._nov._des.".split("_"), n = "jan_feb_mrt_apr_mai_jun_jul_aug_sep_okt_nov_des".split("_"), a = e.defineLocale("fy", {
            months: "jannewaris_febrewaris_maart_april_maaie_juny_july_augustus_septimber_oktober_novimber_desimber".split("_"),
            monthsShort: function (e, a) {
                return /-MMM-/.test(a) ? n[e.month()] : t[e.month()]
            },
            monthsParseExact: !0,
            weekdays: "snein_moandei_tiisdei_woansdei_tongersdei_freed_sneon".split("_"),
            weekdaysShort: "si._mo._ti._wo._to._fr._so.".split("_"),
            weekdaysMin: "Si_Mo_Ti_Wo_To_Fr_So".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD-MM-YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[hjoed om] LT",
                nextDay: "[moarn om] LT",
                nextWeek: "dddd [om] LT",
                lastDay: "[juster om] LT",
                lastWeek: "[ôfrûne] dddd [om] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "oer %s",
                past: "%s lyn",
                s: "in pear sekonden",
                m: "ien minút",
                mm: "%d minuten",
                h: "ien oere",
                hh: "%d oeren",
                d: "ien dei",
                dd: "%d dagen",
                M: "ien moanne",
                MM: "%d moannen",
                y: "ien jier",
                yy: "%d jierren"
            },
            ordinalParse: /\d{1,2}(ste|de)/,
            ordinal: function (e) {
                return e + (1 === e || 8 === e || e >= 20 ? "ste" : "de")
            },
            week: {dow: 1, doy: 4}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = ["Am Faoilleach", "An Gearran", "Am Màrt", "An Giblean", "An Cèitean", "An t-Ògmhios", "An t-Iuchar", "An Lùnastal", "An t-Sultain", "An Dàmhair", "An t-Samhain", "An Dùbhlachd"], n = ["Faoi", "Gear", "Màrt", "Gibl", "Cèit", "Ògmh", "Iuch", "Lùn", "Sult", "Dàmh", "Samh", "Dùbh"], a = ["Didòmhnaich", "Diluain", "Dimàirt", "Diciadain", "Diardaoin", "Dihaoine", "Disathairne"], i = ["Did", "Dil", "Dim", "Dic", "Dia", "Dih", "Dis"], o = ["Dò", "Lu", "Mà", "Ci", "Ar", "Ha", "Sa"], r = e.defineLocale("gd", {
            months: t,
            monthsShort: n,
            monthsParseExact: !0,
            weekdays: a,
            weekdaysShort: i,
            weekdaysMin: o,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[An-diugh aig] LT",
                nextDay: "[A-màireach aig] LT",
                nextWeek: "dddd [aig] LT",
                lastDay: "[An-dè aig] LT",
                lastWeek: "dddd [seo chaidh] [aig] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "ann an %s",
                past: "bho chionn %s",
                s: "beagan diogan",
                m: "mionaid",
                mm: "%d mionaidean",
                h: "uair",
                hh: "%d uairean",
                d: "latha",
                dd: "%d latha",
                M: "mìos",
                MM: "%d mìosan",
                y: "bliadhna",
                yy: "%d bliadhna"
            },
            ordinalParse: /\d{1,2}(d|na|mh)/,
            ordinal: function (e) {
                var t = 1 === e ? "d" : e % 10 === 2 ? "na" : "mh";
                return e + t
            },
            week: {dow: 1, doy: 4}
        });
        return r
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("gl", {
            months: "xaneiro_febreiro_marzo_abril_maio_xuño_xullo_agosto_setembro_outubro_novembro_decembro".split("_"),
            monthsShort: "xan._feb._mar._abr._mai._xuñ._xul._ago._set._out._nov._dec.".split("_"),
            monthsParseExact: !0,
            weekdays: "domingo_luns_martes_mércores_xoves_venres_sábado".split("_"),
            weekdaysShort: "dom._lun._mar._mér._xov._ven._sáb.".split("_"),
            weekdaysMin: "do_lu_ma_mé_xo_ve_sá".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D [de] MMMM [de] YYYY",
                LLL: "D [de] MMMM [de] YYYY H:mm",
                LLLL: "dddd, D [de] MMMM [de] YYYY H:mm"
            },
            calendar: {
                sameDay: function () {
                    return "[hoxe " + (1 !== this.hours() ? "ás" : "á") + "] LT"
                }, nextDay: function () {
                    return "[mañá " + (1 !== this.hours() ? "ás" : "á") + "] LT"
                }, nextWeek: function () {
                    return "dddd [" + (1 !== this.hours() ? "ás" : "a") + "] LT"
                }, lastDay: function () {
                    return "[onte " + (1 !== this.hours() ? "á" : "a") + "] LT"
                }, lastWeek: function () {
                    return "[o] dddd [pasado " + (1 !== this.hours() ? "ás" : "a") + "] LT"
                }, sameElse: "L"
            },
            relativeTime: {
                future: function (e) {
                    return 0 === e.indexOf("un") ? "n" + e : "en " + e
                },
                past: "hai %s",
                s: "uns segundos",
                m: "un minuto",
                mm: "%d minutos",
                h: "unha hora",
                hh: "%d horas",
                d: "un día",
                dd: "%d días",
                M: "un mes",
                MM: "%d meses",
                y: "un ano",
                yy: "%d anos"
            },
            ordinalParse: /\d{1,2}º/,
            ordinal: "%dº",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("he", {
            months: "ינואר_פברואר_מרץ_אפריל_מאי_יוני_יולי_אוגוסט_ספטמבר_אוקטובר_נובמבר_דצמבר".split("_"),
            monthsShort: "ינו׳_פבר׳_מרץ_אפר׳_מאי_יוני_יולי_אוג׳_ספט׳_אוק׳_נוב׳_דצמ׳".split("_"),
            weekdays: "ראשון_שני_שלישי_רביעי_חמישי_שישי_שבת".split("_"),
            weekdaysShort: "א׳_ב׳_ג׳_ד׳_ה׳_ו׳_ש׳".split("_"),
            weekdaysMin: "א_ב_ג_ד_ה_ו_ש".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D [ב]MMMM YYYY",
                LLL: "D [ב]MMMM YYYY HH:mm",
                LLLL: "dddd, D [ב]MMMM YYYY HH:mm",
                l: "D/M/YYYY",
                ll: "D MMM YYYY",
                lll: "D MMM YYYY HH:mm",
                llll: "ddd, D MMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[היום ב־]LT",
                nextDay: "[מחר ב־]LT",
                nextWeek: "dddd [בשעה] LT",
                lastDay: "[אתמול ב־]LT",
                lastWeek: "[ביום] dddd [האחרון בשעה] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "בעוד %s",
                past: "לפני %s",
                s: "מספר שניות",
                m: "דקה",
                mm: "%d דקות",
                h: "שעה",
                hh: function (e) {
                    return 2 === e ? "שעתיים" : e + " שעות"
                },
                d: "יום",
                dd: function (e) {
                    return 2 === e ? "יומיים" : e + " ימים"
                },
                M: "חודש",
                MM: function (e) {
                    return 2 === e ? "חודשיים" : e + " חודשים"
                },
                y: "שנה",
                yy: function (e) {
                    return 2 === e ? "שנתיים" : e % 10 === 0 && 10 !== e ? e + " שנה" : e + " שנים"
                }
            },
            meridiemParse: /אחה"צ|לפנה"צ|אחרי הצהריים|לפני הצהריים|לפנות בוקר|בבוקר|בערב/i,
            isPM: function (e) {
                return /^(אחה"צ|אחרי הצהריים|בערב)$/.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 5 ? "לפנות בוקר" : e < 10 ? "בבוקר" : e < 12 ? n ? 'לפנה"צ' : "לפני הצהריים" : e < 18 ? n ? 'אחה"צ' : "אחרי הצהריים" : "בערב"
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "१", 2: "२", 3: "३", 4: "४", 5: "५", 6: "६", 7: "७", 8: "८", 9: "९", 0: "०"}, n = {
            "१": "1",
            "२": "2",
            "३": "3",
            "४": "4",
            "५": "5",
            "६": "6",
            "७": "7",
            "८": "8",
            "९": "9",
            "०": "0"
        }, a = e.defineLocale("hi", {
            months: "जनवरी_फ़रवरी_मार्च_अप्रैल_मई_जून_जुलाई_अगस्त_सितम्बर_अक्टूबर_नवम्बर_दिसम्बर".split("_"),
            monthsShort: "जन._फ़र._मार्च_अप्रै._मई_जून_जुल._अग._सित._अक्टू._नव._दिस.".split("_"),
            monthsParseExact: !0,
            weekdays: "रविवार_सोमवार_मंगलवार_बुधवार_गुरूवार_शुक्रवार_शनिवार".split("_"),
            weekdaysShort: "रवि_सोम_मंगल_बुध_गुरू_शुक्र_शनि".split("_"),
            weekdaysMin: "र_सो_मं_बु_गु_शु_श".split("_"),
            longDateFormat: {
                LT: "A h:mm बजे",
                LTS: "A h:mm:ss बजे",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, A h:mm बजे",
                LLLL: "dddd, D MMMM YYYY, A h:mm बजे"
            },
            calendar: {
                sameDay: "[आज] LT",
                nextDay: "[कल] LT",
                nextWeek: "dddd, LT",
                lastDay: "[कल] LT",
                lastWeek: "[पिछले] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s में",
                past: "%s पहले",
                s: "कुछ ही क्षण",
                m: "एक मिनट",
                mm: "%d मिनट",
                h: "एक घंटा",
                hh: "%d घंटे",
                d: "एक दिन",
                dd: "%d दिन",
                M: "एक महीने",
                MM: "%d महीने",
                y: "एक वर्ष",
                yy: "%d वर्ष"
            },
            preparse: function (e) {
                return e.replace(/[१२३४५६७८९०]/g, function (e) {
                    return n[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                })
            },
            meridiemParse: /रात|सुबह|दोपहर|शाम/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "रात" === t ? e < 4 ? e : e + 12 : "सुबह" === t ? e : "दोपहर" === t ? e >= 10 ? e : e + 12 : "शाम" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "रात" : e < 10 ? "सुबह" : e < 17 ? "दोपहर" : e < 20 ? "शाम" : "रात"
            },
            week: {dow: 0, doy: 6}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n) {
            var a = e + " ";
            switch (n) {
                case"m":
                    return t ? "jedna minuta" : "jedne minute";
                case"mm":
                    return a += 1 === e ? "minuta" : 2 === e || 3 === e || 4 === e ? "minute" : "minuta";
                case"h":
                    return t ? "jedan sat" : "jednog sata";
                case"hh":
                    return a += 1 === e ? "sat" : 2 === e || 3 === e || 4 === e ? "sata" : "sati";
                case"dd":
                    return a += 1 === e ? "dan" : "dana";
                case"MM":
                    return a += 1 === e ? "mjesec" : 2 === e || 3 === e || 4 === e ? "mjeseca" : "mjeseci";
                case"yy":
                    return a += 1 === e ? "godina" : 2 === e || 3 === e || 4 === e ? "godine" : "godina"
            }
        }

        var n = e.defineLocale("hr", {
            months: {
                format: "siječnja_veljače_ožujka_travnja_svibnja_lipnja_srpnja_kolovoza_rujna_listopada_studenoga_prosinca".split("_"),
                standalone: "siječanj_veljača_ožujak_travanj_svibanj_lipanj_srpanj_kolovoz_rujan_listopad_studeni_prosinac".split("_")
            },
            monthsShort: "sij._velj._ožu._tra._svi._lip._srp._kol._ruj._lis._stu._pro.".split("_"),
            monthsParseExact: !0,
            weekdays: "nedjelja_ponedjeljak_utorak_srijeda_četvrtak_petak_subota".split("_"),
            weekdaysShort: "ned._pon._uto._sri._čet._pet._sub.".split("_"),
            weekdaysMin: "ne_po_ut_sr_če_pe_su".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd, D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[danas u] LT", nextDay: "[sutra u] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[u] [nedjelju] [u] LT";
                        case 3:
                            return "[u] [srijedu] [u] LT";
                        case 6:
                            return "[u] [subotu] [u] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[u] dddd [u] LT"
                    }
                }, lastDay: "[jučer u] LT", lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                        case 3:
                            return "[prošlu] dddd [u] LT";
                        case 6:
                            return "[prošle] [subote] [u] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[prošli] dddd [u] LT"
                    }
                }, sameElse: "L"
            },
            relativeTime: {
                future: "za %s",
                past: "prije %s",
                s: "par sekundi",
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: "dan",
                dd: t,
                M: "mjesec",
                MM: t,
                y: "godinu",
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = e;
            switch (n) {
                case"s":
                    return a || t ? "néhány másodperc" : "néhány másodperce";
                case"m":
                    return "egy" + (a || t ? " perc" : " perce");
                case"mm":
                    return i + (a || t ? " perc" : " perce");
                case"h":
                    return "egy" + (a || t ? " óra" : " órája");
                case"hh":
                    return i + (a || t ? " óra" : " órája");
                case"d":
                    return "egy" + (a || t ? " nap" : " napja");
                case"dd":
                    return i + (a || t ? " nap" : " napja");
                case"M":
                    return "egy" + (a || t ? " hónap" : " hónapja");
                case"MM":
                    return i + (a || t ? " hónap" : " hónapja");
                case"y":
                    return "egy" + (a || t ? " év" : " éve");
                case"yy":
                    return i + (a || t ? " év" : " éve")
            }
            return ""
        }

        function n(e) {
            return (e ? "" : "[múlt] ") + "[" + a[this.day()] + "] LT[-kor]"
        }

        var a = "vasárnap hétfőn kedden szerdán csütörtökön pénteken szombaton".split(" "), i = e.defineLocale("hu", {
            months: "január_február_március_április_május_június_július_augusztus_szeptember_október_november_december".split("_"),
            monthsShort: "jan_feb_márc_ápr_máj_jún_júl_aug_szept_okt_nov_dec".split("_"),
            weekdays: "vasárnap_hétfő_kedd_szerda_csütörtök_péntek_szombat".split("_"),
            weekdaysShort: "vas_hét_kedd_sze_csüt_pén_szo".split("_"),
            weekdaysMin: "v_h_k_sze_cs_p_szo".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "YYYY.MM.DD.",
                LL: "YYYY. MMMM D.",
                LLL: "YYYY. MMMM D. H:mm",
                LLLL: "YYYY. MMMM D., dddd H:mm"
            },
            meridiemParse: /de|du/i,
            isPM: function (e) {
                return "u" === e.charAt(1).toLowerCase()
            },
            meridiem: function (e, t, n) {
                return e < 12 ? n === !0 ? "de" : "DE" : n === !0 ? "du" : "DU"
            },
            calendar: {
                sameDay: "[ma] LT[-kor]", nextDay: "[holnap] LT[-kor]", nextWeek: function () {
                    return n.call(this, !0)
                }, lastDay: "[tegnap] LT[-kor]", lastWeek: function () {
                    return n.call(this, !1)
                }, sameElse: "L"
            },
            relativeTime: {
                future: "%s múlva",
                past: "%s",
                s: t,
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: t,
                dd: t,
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return i
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("hy-am", {
            months: {
                format: "հունվարի_փետրվարի_մարտի_ապրիլի_մայիսի_հունիսի_հուլիսի_օգոստոսի_սեպտեմբերի_հոկտեմբերի_նոյեմբերի_դեկտեմբերի".split("_"),
                standalone: "հունվար_փետրվար_մարտ_ապրիլ_մայիս_հունիս_հուլիս_օգոստոս_սեպտեմբեր_հոկտեմբեր_նոյեմբեր_դեկտեմբեր".split("_")
            },
            monthsShort: "հնվ_փտր_մրտ_ապր_մյս_հնս_հլս_օգս_սպտ_հկտ_նմբ_դկտ".split("_"),
            weekdays: "կիրակի_երկուշաբթի_երեքշաբթի_չորեքշաբթի_հինգշաբթի_ուրբաթ_շաբաթ".split("_"),
            weekdaysShort: "կրկ_երկ_երք_չրք_հնգ_ուրբ_շբթ".split("_"),
            weekdaysMin: "կրկ_երկ_երք_չրք_հնգ_ուրբ_շբթ".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY թ.",
                LLL: "D MMMM YYYY թ., HH:mm",
                LLLL: "dddd, D MMMM YYYY թ., HH:mm"
            },
            calendar: {
                sameDay: "[այսօր] LT", nextDay: "[վաղը] LT", lastDay: "[երեկ] LT", nextWeek: function () {
                    return "dddd [օրը ժամը] LT"
                }, lastWeek: function () {
                    return "[անցած] dddd [օրը ժամը] LT"
                }, sameElse: "L"
            },
            relativeTime: {
                future: "%s հետո",
                past: "%s առաջ",
                s: "մի քանի վայրկյան",
                m: "րոպե",
                mm: "%d րոպե",
                h: "ժամ",
                hh: "%d ժամ",
                d: "օր",
                dd: "%d օր",
                M: "ամիս",
                MM: "%d ամիս",
                y: "տարի",
                yy: "%d տարի"
            },
            meridiemParse: /գիշերվա|առավոտվա|ցերեկվա|երեկոյան/,
            isPM: function (e) {
                return /^(ցերեկվա|երեկոյան)$/.test(e)
            },
            meridiem: function (e) {
                return e < 4 ? "գիշերվա" : e < 12 ? "առավոտվա" : e < 17 ? "ցերեկվա" : "երեկոյան"
            },
            ordinalParse: /\d{1,2}|\d{1,2}-(ին|րդ)/,
            ordinal: function (e, t) {
                switch (t) {
                    case"DDD":
                    case"w":
                    case"W":
                    case"DDDo":
                        return 1 === e ? e + "-ին" : e + "-րդ";
                    default:
                        return e
                }
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("id", {
            months: "Januari_Februari_Maret_April_Mei_Juni_Juli_Agustus_September_Oktober_November_Desember".split("_"),
            monthsShort: "Jan_Feb_Mar_Apr_Mei_Jun_Jul_Ags_Sep_Okt_Nov_Des".split("_"),
            weekdays: "Minggu_Senin_Selasa_Rabu_Kamis_Jumat_Sabtu".split("_"),
            weekdaysShort: "Min_Sen_Sel_Rab_Kam_Jum_Sab".split("_"),
            weekdaysMin: "Mg_Sn_Sl_Rb_Km_Jm_Sb".split("_"),
            longDateFormat: {
                LT: "HH.mm",
                LTS: "HH.mm.ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY [pukul] HH.mm",
                LLLL: "dddd, D MMMM YYYY [pukul] HH.mm"
            },
            meridiemParse: /pagi|siang|sore|malam/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "pagi" === t ? e : "siang" === t ? e >= 11 ? e : e + 12 : "sore" === t || "malam" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 11 ? "pagi" : e < 15 ? "siang" : e < 19 ? "sore" : "malam"
            },
            calendar: {
                sameDay: "[Hari ini pukul] LT",
                nextDay: "[Besok pukul] LT",
                nextWeek: "dddd [pukul] LT",
                lastDay: "[Kemarin pukul] LT",
                lastWeek: "dddd [lalu pukul] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "dalam %s",
                past: "%s yang lalu",
                s: "beberapa detik",
                m: "semenit",
                mm: "%d menit",
                h: "sejam",
                hh: "%d jam",
                d: "sehari",
                dd: "%d hari",
                M: "sebulan",
                MM: "%d bulan",
                y: "setahun",
                yy: "%d tahun"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e) {
            return e % 100 === 11 || e % 10 !== 1
        }

        function n(e, n, a, i) {
            var o = e + " ";
            switch (a) {
                case"s":
                    return n || i ? "nokkrar sekúndur" : "nokkrum sekúndum";
                case"m":
                    return n ? "mínúta" : "mínútu";
                case"mm":
                    return t(e) ? o + (n || i ? "mínútur" : "mínútum") : n ? o + "mínúta" : o + "mínútu";
                case"hh":
                    return t(e) ? o + (n || i ? "klukkustundir" : "klukkustundum") : o + "klukkustund";
                case"d":
                    return n ? "dagur" : i ? "dag" : "degi";
                case"dd":
                    return t(e) ? n ? o + "dagar" : o + (i ? "daga" : "dögum") : n ? o + "dagur" : o + (i ? "dag" : "degi");
                case"M":
                    return n ? "mánuður" : i ? "mánuð" : "mánuði";
                case"MM":
                    return t(e) ? n ? o + "mánuðir" : o + (i ? "mánuði" : "mánuðum") : n ? o + "mánuður" : o + (i ? "mánuð" : "mánuði");
                case"y":
                    return n || i ? "ár" : "ári";
                case"yy":
                    return t(e) ? o + (n || i ? "ár" : "árum") : o + (n || i ? "ár" : "ári")
            }
        }

        var a = e.defineLocale("is", {
            months: "janúar_febrúar_mars_apríl_maí_júní_júlí_ágúst_september_október_nóvember_desember".split("_"),
            monthsShort: "jan_feb_mar_apr_maí_jún_júl_ágú_sep_okt_nóv_des".split("_"),
            weekdays: "sunnudagur_mánudagur_þriðjudagur_miðvikudagur_fimmtudagur_föstudagur_laugardagur".split("_"),
            weekdaysShort: "sun_mán_þri_mið_fim_fös_lau".split("_"),
            weekdaysMin: "Su_Má_Þr_Mi_Fi_Fö_La".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY [kl.] H:mm",
                LLLL: "dddd, D. MMMM YYYY [kl.] H:mm"
            },
            calendar: {
                sameDay: "[í dag kl.] LT",
                nextDay: "[á morgun kl.] LT",
                nextWeek: "dddd [kl.] LT",
                lastDay: "[í gær kl.] LT",
                lastWeek: "[síðasta] dddd [kl.] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "eftir %s",
                past: "fyrir %s síðan",
                s: n,
                m: n,
                mm: n,
                h: "klukkustund",
                hh: n,
                d: n,
                dd: n,
                M: n,
                MM: n,
                y: n,
                yy: n
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("it", {
            months: "gennaio_febbraio_marzo_aprile_maggio_giugno_luglio_agosto_settembre_ottobre_novembre_dicembre".split("_"),
            monthsShort: "gen_feb_mar_apr_mag_giu_lug_ago_set_ott_nov_dic".split("_"),
            weekdays: "Domenica_Lunedì_Martedì_Mercoledì_Giovedì_Venerdì_Sabato".split("_"),
            weekdaysShort: "Dom_Lun_Mar_Mer_Gio_Ven_Sab".split("_"),
            weekdaysMin: "Do_Lu_Ma_Me_Gi_Ve_Sa".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Oggi alle] LT",
                nextDay: "[Domani alle] LT",
                nextWeek: "dddd [alle] LT",
                lastDay: "[Ieri alle] LT",
                lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[la scorsa] dddd [alle] LT";
                        default:
                            return "[lo scorso] dddd [alle] LT"
                    }
                },
                sameElse: "L"
            },
            relativeTime: {
                future: function (e) {
                    return (/^[0-9].+$/.test(e) ? "tra" : "in") + " " + e
                },
                past: "%s fa",
                s: "alcuni secondi",
                m: "un minuto",
                mm: "%d minuti",
                h: "un'ora",
                hh: "%d ore",
                d: "un giorno",
                dd: "%d giorni",
                M: "un mese",
                MM: "%d mesi",
                y: "un anno",
                yy: "%d anni"
            },
            ordinalParse: /\d{1,2}º/,
            ordinal: "%dº",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ja", {
            months: "1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月".split("_"),
            monthsShort: "1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月".split("_"),
            weekdays: "日曜日_月曜日_火曜日_水曜日_木曜日_金曜日_土曜日".split("_"),
            weekdaysShort: "日_月_火_水_木_金_土".split("_"),
            weekdaysMin: "日_月_火_水_木_金_土".split("_"),
            longDateFormat: {
                LT: "Ah時m分",
                LTS: "Ah時m分s秒",
                L: "YYYY/MM/DD",
                LL: "YYYY年M月D日",
                LLL: "YYYY年M月D日Ah時m分",
                LLLL: "YYYY年M月D日Ah時m分 dddd"
            },
            meridiemParse: /午前|午後/i,
            isPM: function (e) {
                return "午後" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "午前" : "午後"
            },
            calendar: {
                sameDay: "[今日] LT",
                nextDay: "[明日] LT",
                nextWeek: "[来週]dddd LT",
                lastDay: "[昨日] LT",
                lastWeek: "[前週]dddd LT",
                sameElse: "L"
            },
            ordinalParse: /\d{1,2}日/,
            ordinal: function (e, t) {
                switch (t) {
                    case"d":
                    case"D":
                    case"DDD":
                        return e + "日";
                    default:
                        return e
                }
            },
            relativeTime: {
                future: "%s後",
                past: "%s前",
                s: "数秒",
                m: "1分",
                mm: "%d分",
                h: "1時間",
                hh: "%d時間",
                d: "1日",
                dd: "%d日",
                M: "1ヶ月",
                MM: "%dヶ月",
                y: "1年",
                yy: "%d年"
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("jv", {
            months: "Januari_Februari_Maret_April_Mei_Juni_Juli_Agustus_September_Oktober_Nopember_Desember".split("_"),
            monthsShort: "Jan_Feb_Mar_Apr_Mei_Jun_Jul_Ags_Sep_Okt_Nop_Des".split("_"),
            weekdays: "Minggu_Senen_Seloso_Rebu_Kemis_Jemuwah_Septu".split("_"),
            weekdaysShort: "Min_Sen_Sel_Reb_Kem_Jem_Sep".split("_"),
            weekdaysMin: "Mg_Sn_Sl_Rb_Km_Jm_Sp".split("_"),
            longDateFormat: {
                LT: "HH.mm",
                LTS: "HH.mm.ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY [pukul] HH.mm",
                LLLL: "dddd, D MMMM YYYY [pukul] HH.mm"
            },
            meridiemParse: /enjing|siyang|sonten|ndalu/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "enjing" === t ? e : "siyang" === t ? e >= 11 ? e : e + 12 : "sonten" === t || "ndalu" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 11 ? "enjing" : e < 15 ? "siyang" : e < 19 ? "sonten" : "ndalu"
            },
            calendar: {
                sameDay: "[Dinten puniko pukul] LT",
                nextDay: "[Mbenjang pukul] LT",
                nextWeek: "dddd [pukul] LT",
                lastDay: "[Kala wingi pukul] LT",
                lastWeek: "dddd [kepengker pukul] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "wonten ing %s",
                past: "%s ingkang kepengker",
                s: "sawetawis detik",
                m: "setunggal menit",
                mm: "%d menit",
                h: "setunggal jam",
                hh: "%d jam",
                d: "sedinten",
                dd: "%d dinten",
                M: "sewulan",
                MM: "%d wulan",
                y: "setaun",
                yy: "%d taun"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ka", {
            months: {
                standalone: "იანვარი_თებერვალი_მარტი_აპრილი_მაისი_ივნისი_ივლისი_აგვისტო_სექტემბერი_ოქტომბერი_ნოემბერი_დეკემბერი".split("_"),
                format: "იანვარს_თებერვალს_მარტს_აპრილის_მაისს_ივნისს_ივლისს_აგვისტს_სექტემბერს_ოქტომბერს_ნოემბერს_დეკემბერს".split("_")
            },
            monthsShort: "იან_თებ_მარ_აპრ_მაი_ივნ_ივლ_აგვ_სექ_ოქტ_ნოე_დეკ".split("_"),
            weekdays: {
                standalone: "კვირა_ორშაბათი_სამშაბათი_ოთხშაბათი_ხუთშაბათი_პარასკევი_შაბათი".split("_"),
                format: "კვირას_ორშაბათს_სამშაბათს_ოთხშაბათს_ხუთშაბათს_პარასკევს_შაბათს".split("_"),
                isFormat: /(წინა|შემდეგ)/
            },
            weekdaysShort: "კვი_ორშ_სამ_ოთხ_ხუთ_პარ_შაბ".split("_"),
            weekdaysMin: "კვ_ორ_სა_ოთ_ხუ_პა_შა".split("_"),
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY h:mm A",
                LLLL: "dddd, D MMMM YYYY h:mm A"
            },
            calendar: {
                sameDay: "[დღეს] LT[-ზე]",
                nextDay: "[ხვალ] LT[-ზე]",
                lastDay: "[გუშინ] LT[-ზე]",
                nextWeek: "[შემდეგ] dddd LT[-ზე]",
                lastWeek: "[წინა] dddd LT-ზე",
                sameElse: "L"
            },
            relativeTime: {
                future: function (e) {
                    return /(წამი|წუთი|საათი|წელი)/.test(e) ? e.replace(/ი$/, "ში") : e + "ში"
                },
                past: function (e) {
                    return /(წამი|წუთი|საათი|დღე|თვე)/.test(e) ? e.replace(/(ი|ე)$/, "ის წინ") : /წელი/.test(e) ? e.replace(/წელი$/, "წლის წინ") : void 0
                },
                s: "რამდენიმე წამი",
                m: "წუთი",
                mm: "%d წუთი",
                h: "საათი",
                hh: "%d საათი",
                d: "დღე",
                dd: "%d დღე",
                M: "თვე",
                MM: "%d თვე",
                y: "წელი",
                yy: "%d წელი"
            },
            ordinalParse: /0|1-ლი|მე-\d{1,2}|\d{1,2}-ე/,
            ordinal: function (e) {
                return 0 === e ? e : 1 === e ? e + "-ლი" : e < 20 || e <= 100 && e % 20 === 0 || e % 100 === 0 ? "მე-" + e : e + "-ე"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {
            0: "-ші",
            1: "-ші",
            2: "-ші",
            3: "-ші",
            4: "-ші",
            5: "-ші",
            6: "-шы",
            7: "-ші",
            8: "-ші",
            9: "-шы",
            10: "-шы",
            20: "-шы",
            30: "-шы",
            40: "-шы",
            50: "-ші",
            60: "-шы",
            70: "-ші",
            80: "-ші",
            90: "-шы",
            100: "-ші"
        }, n = e.defineLocale("kk", {
            months: "қаңтар_ақпан_наурыз_сәуір_мамыр_маусым_шілде_тамыз_қыркүйек_қазан_қараша_желтоқсан".split("_"),
            monthsShort: "қаң_ақп_нау_сәу_мам_мау_шіл_там_қыр_қаз_қар_жел".split("_"),
            weekdays: "жексенбі_дүйсенбі_сейсенбі_сәрсенбі_бейсенбі_жұма_сенбі".split("_"),
            weekdaysShort: "жек_дүй_сей_сәр_бей_жұм_сен".split("_"),
            weekdaysMin: "жк_дй_сй_ср_бй_жм_сн".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Бүгін сағат] LT",
                nextDay: "[Ертең сағат] LT",
                nextWeek: "dddd [сағат] LT",
                lastDay: "[Кеше сағат] LT",
                lastWeek: "[Өткен аптаның] dddd [сағат] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s ішінде",
                past: "%s бұрын",
                s: "бірнеше секунд",
                m: "бір минут",
                mm: "%d минут",
                h: "бір сағат",
                hh: "%d сағат",
                d: "бір күн",
                dd: "%d күн",
                M: "бір ай",
                MM: "%d ай",
                y: "бір жыл",
                yy: "%d жыл"
            },
            ordinalParse: /\d{1,2}-(ші|шы)/,
            ordinal: function (e) {
                var n = e % 10, a = e >= 100 ? 100 : null;
                return e + (t[e] || t[n] || t[a])
            },
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("km", {
            months: "មករា_កុម្ភៈ_មីនា_មេសា_ឧសភា_មិថុនា_កក្កដា_សីហា_កញ្ញា_តុលា_វិច្ឆិកា_ធ្នូ".split("_"),
            monthsShort: "មករា_កុម្ភៈ_មីនា_មេសា_ឧសភា_មិថុនា_កក្កដា_សីហា_កញ្ញា_តុលា_វិច្ឆិកា_ធ្នូ".split("_"),
            weekdays: "អាទិត្យ_ច័ន្ទ_អង្គារ_ពុធ_ព្រហស្បតិ៍_សុក្រ_សៅរ៍".split("_"),
            weekdaysShort: "អាទិត្យ_ច័ន្ទ_អង្គារ_ពុធ_ព្រហស្បតិ៍_សុក្រ_សៅរ៍".split("_"),
            weekdaysMin: "អាទិត្យ_ច័ន្ទ_អង្គារ_ពុធ_ព្រហស្បតិ៍_សុក្រ_សៅរ៍".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[ថ្ងៃនេះ ម៉ោង] LT",
                nextDay: "[ស្អែក ម៉ោង] LT",
                nextWeek: "dddd [ម៉ោង] LT",
                lastDay: "[ម្សិលមិញ ម៉ោង] LT",
                lastWeek: "dddd [សប្តាហ៍មុន] [ម៉ោង] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%sទៀត",
                past: "%sមុន",
                s: "ប៉ុន្មានវិនាទី",
                m: "មួយនាទី",
                mm: "%d នាទី",
                h: "មួយម៉ោង",
                hh: "%d ម៉ោង",
                d: "មួយថ្ងៃ",
                dd: "%d ថ្ងៃ",
                M: "មួយខែ",
                MM: "%d ខែ",
                y: "មួយឆ្នាំ",
                yy: "%d ឆ្នាំ"
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ko", {
            months: "1월_2월_3월_4월_5월_6월_7월_8월_9월_10월_11월_12월".split("_"),
            monthsShort: "1월_2월_3월_4월_5월_6월_7월_8월_9월_10월_11월_12월".split("_"),
            weekdays: "일요일_월요일_화요일_수요일_목요일_금요일_토요일".split("_"),
            weekdaysShort: "일_월_화_수_목_금_토".split("_"),
            weekdaysMin: "일_월_화_수_목_금_토".split("_"),
            longDateFormat: {
                LT: "A h시 m분",
                LTS: "A h시 m분 s초",
                L: "YYYY.MM.DD",
                LL: "YYYY년 MMMM D일",
                LLL: "YYYY년 MMMM D일 A h시 m분",
                LLLL: "YYYY년 MMMM D일 dddd A h시 m분"
            },
            calendar: {
                sameDay: "오늘 LT",
                nextDay: "내일 LT",
                nextWeek: "dddd LT",
                lastDay: "어제 LT",
                lastWeek: "지난주 dddd LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s 후",
                past: "%s 전",
                s: "몇 초",
                ss: "%d초",
                m: "일분",
                mm: "%d분",
                h: "한 시간",
                hh: "%d시간",
                d: "하루",
                dd: "%d일",
                M: "한 달",
                MM: "%d달",
                y: "일 년",
                yy: "%d년"
            },
            ordinalParse: /\d{1,2}일/,
            ordinal: "%d일",
            meridiemParse: /오전|오후/,
            isPM: function (e) {
                return "오후" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "오전" : "오후"
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {
            0: "-чү",
            1: "-чи",
            2: "-чи",
            3: "-чү",
            4: "-чү",
            5: "-чи",
            6: "-чы",
            7: "-чи",
            8: "-чи",
            9: "-чу",
            10: "-чу",
            20: "-чы",
            30: "-чу",
            40: "-чы",
            50: "-чү",
            60: "-чы",
            70: "-чи",
            80: "-чи",
            90: "-чу",
            100: "-чү"
        }, n = e.defineLocale("ky", {
            months: "январь_февраль_март_апрель_май_июнь_июль_август_сентябрь_октябрь_ноябрь_декабрь".split("_"),
            monthsShort: "янв_фев_март_апр_май_июнь_июль_авг_сен_окт_ноя_дек".split("_"),
            weekdays: "Жекшемби_Дүйшөмбү_Шейшемби_Шаршемби_Бейшемби_Жума_Ишемби".split("_"),
            weekdaysShort: "Жек_Дүй_Шей_Шар_Бей_Жум_Ише".split("_"),
            weekdaysMin: "Жк_Дй_Шй_Шр_Бй_Жм_Иш".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Бүгүн саат] LT",
                nextDay: "[Эртең саат] LT",
                nextWeek: "dddd [саат] LT",
                lastDay: "[Кече саат] LT",
                lastWeek: "[Өткен аптанын] dddd [күнү] [саат] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s ичинде",
                past: "%s мурун",
                s: "бирнече секунд",
                m: "бир мүнөт",
                mm: "%d мүнөт",
                h: "бир саат",
                hh: "%d саат",
                d: "бир күн",
                dd: "%d күн",
                M: "бир ай",
                MM: "%d ай",
                y: "бир жыл",
                yy: "%d жыл"
            },
            ordinalParse: /\d{1,2}-(чи|чы|чү|чу)/,
            ordinal: function (e) {
                var n = e % 10, a = e >= 100 ? 100 : null;
                return e + (t[e] || t[n] || t[a])
            },
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = {
                m: ["eng Minutt", "enger Minutt"],
                h: ["eng Stonn", "enger Stonn"],
                d: ["een Dag", "engem Dag"],
                M: ["ee Mount", "engem Mount"],
                y: ["ee Joer", "engem Joer"]
            };
            return t ? i[n][0] : i[n][1]
        }

        function n(e) {
            var t = e.substr(0, e.indexOf(" "));
            return i(t) ? "a " + e : "an " + e
        }

        function a(e) {
            var t = e.substr(0, e.indexOf(" "));
            return i(t) ? "viru " + e : "virun " + e
        }

        function i(e) {
            if (e = parseInt(e, 10), isNaN(e))return !1;
            if (e < 0)return !0;
            if (e < 10)return 4 <= e && e <= 7;
            if (e < 100) {
                var t = e % 10, n = e / 10;
                return i(0 === t ? n : t)
            }
            if (e < 1e4) {
                for (; e >= 10;)e /= 10;
                return i(e)
            }
            return e /= 1e3, i(e)
        }

        var o = e.defineLocale("lb", {
            months: "Januar_Februar_Mäerz_Abrëll_Mee_Juni_Juli_August_September_Oktober_November_Dezember".split("_"),
            monthsShort: "Jan._Febr._Mrz._Abr._Mee_Jun._Jul._Aug._Sept._Okt._Nov._Dez.".split("_"),
            monthsParseExact: !0,
            weekdays: "Sonndeg_Méindeg_Dënschdeg_Mëttwoch_Donneschdeg_Freideg_Samschdeg".split("_"),
            weekdaysShort: "So._Mé._Dë._Më._Do._Fr._Sa.".split("_"),
            weekdaysMin: "So_Mé_Dë_Më_Do_Fr_Sa".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm [Auer]",
                LTS: "H:mm:ss [Auer]",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm [Auer]",
                LLLL: "dddd, D. MMMM YYYY H:mm [Auer]"
            },
            calendar: {
                sameDay: "[Haut um] LT",
                sameElse: "L",
                nextDay: "[Muer um] LT",
                nextWeek: "dddd [um] LT",
                lastDay: "[Gëschter um] LT",
                lastWeek: function () {
                    switch (this.day()) {
                        case 2:
                        case 4:
                            return "[Leschten] dddd [um] LT";
                        default:
                            return "[Leschte] dddd [um] LT"
                    }
                }
            },
            relativeTime: {
                future: n,
                past: a,
                s: "e puer Sekonnen",
                m: t,
                mm: "%d Minutten",
                h: t,
                hh: "%d Stonnen",
                d: t,
                dd: "%d Deeg",
                M: t,
                MM: "%d Méint",
                y: t,
                yy: "%d Joer"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("lo", {
            months: "ມັງກອນ_ກຸມພາ_ມີນາ_ເມສາ_ພຶດສະພາ_ມິຖຸນາ_ກໍລະກົດ_ສິງຫາ_ກັນຍາ_ຕຸລາ_ພະຈິກ_ທັນວາ".split("_"),
            monthsShort: "ມັງກອນ_ກຸມພາ_ມີນາ_ເມສາ_ພຶດສະພາ_ມິຖຸນາ_ກໍລະກົດ_ສິງຫາ_ກັນຍາ_ຕຸລາ_ພະຈິກ_ທັນວາ".split("_"),
            weekdays: "ອາທິດ_ຈັນ_ອັງຄານ_ພຸດ_ພະຫັດ_ສຸກ_ເສົາ".split("_"),
            weekdaysShort: "ທິດ_ຈັນ_ອັງຄານ_ພຸດ_ພະຫັດ_ສຸກ_ເສົາ".split("_"),
            weekdaysMin: "ທ_ຈ_ອຄ_ພ_ພຫ_ສກ_ສ".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "ວັນdddd D MMMM YYYY HH:mm"
            },
            meridiemParse: /ຕອນເຊົ້າ|ຕອນແລງ/,
            isPM: function (e) {
                return "ຕອນແລງ" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "ຕອນເຊົ້າ" : "ຕອນແລງ"
            },
            calendar: {
                sameDay: "[ມື້ນີ້ເວລາ] LT",
                nextDay: "[ມື້ອື່ນເວລາ] LT",
                nextWeek: "[ວັນ]dddd[ໜ້າເວລາ] LT",
                lastDay: "[ມື້ວານນີ້ເວລາ] LT",
                lastWeek: "[ວັນ]dddd[ແລ້ວນີ້ເວລາ] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "ອີກ %s",
                past: "%sຜ່ານມາ",
                s: "ບໍ່ເທົ່າໃດວິນາທີ",
                m: "1 ນາທີ",
                mm: "%d ນາທີ",
                h: "1 ຊົ່ວໂມງ",
                hh: "%d ຊົ່ວໂມງ",
                d: "1 ມື້",
                dd: "%d ມື້",
                M: "1 ເດືອນ",
                MM: "%d ເດືອນ",
                y: "1 ປີ",
                yy: "%d ປີ"
            },
            ordinalParse: /(ທີ່)\d{1,2}/,
            ordinal: function (e) {
                return "ທີ່" + e
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            return t ? "kelios sekundės" : a ? "kelių sekundžių" : "kelias sekundes"
        }

        function n(e, t, n, a) {
            return t ? i(n)[0] : a ? i(n)[1] : i(n)[2]
        }

        function a(e) {
            return e % 10 === 0 || e > 10 && e < 20
        }

        function i(e) {
            return r[e].split("_")
        }

        function o(e, t, o, r) {
            var s = e + " ";
            return 1 === e ? s + n(e, t, o[0], r) : t ? s + (a(e) ? i(o)[1] : i(o)[0]) : r ? s + i(o)[1] : s + (a(e) ? i(o)[1] : i(o)[2])
        }

        var r = {
            m: "minutė_minutės_minutę",
            mm: "minutės_minučių_minutes",
            h: "valanda_valandos_valandą",
            hh: "valandos_valandų_valandas",
            d: "diena_dienos_dieną",
            dd: "dienos_dienų_dienas",
            M: "mėnuo_mėnesio_mėnesį",
            MM: "mėnesiai_mėnesių_mėnesius",
            y: "metai_metų_metus",
            yy: "metai_metų_metus"
        }, s = e.defineLocale("lt", {
            months: {
                format: "sausio_vasario_kovo_balandžio_gegužės_birželio_liepos_rugpjūčio_rugsėjo_spalio_lapkričio_gruodžio".split("_"),
                standalone: "sausis_vasaris_kovas_balandis_gegužė_birželis_liepa_rugpjūtis_rugsėjis_spalis_lapkritis_gruodis".split("_"),
                isFormat: /D[oD]?(\[[^\[\]]*\]|\s)+MMMM?|MMMM?(\[[^\[\]]*\]|\s)+D[oD]?/
            },
            monthsShort: "sau_vas_kov_bal_geg_bir_lie_rgp_rgs_spa_lap_grd".split("_"),
            weekdays: {
                format: "sekmadienį_pirmadienį_antradienį_trečiadienį_ketvirtadienį_penktadienį_šeštadienį".split("_"),
                standalone: "sekmadienis_pirmadienis_antradienis_trečiadienis_ketvirtadienis_penktadienis_šeštadienis".split("_"),
                isFormat: /dddd HH:mm/
            },
            weekdaysShort: "Sek_Pir_Ant_Tre_Ket_Pen_Šeš".split("_"),
            weekdaysMin: "S_P_A_T_K_Pn_Š".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "YYYY-MM-DD",
                LL: "YYYY [m.] MMMM D [d.]",
                LLL: "YYYY [m.] MMMM D [d.], HH:mm [val.]",
                LLLL: "YYYY [m.] MMMM D [d.], dddd, HH:mm [val.]",
                l: "YYYY-MM-DD",
                ll: "YYYY [m.] MMMM D [d.]",
                lll: "YYYY [m.] MMMM D [d.], HH:mm [val.]",
                llll: "YYYY [m.] MMMM D [d.], ddd, HH:mm [val.]"
            },
            calendar: {
                sameDay: "[Šiandien] LT",
                nextDay: "[Rytoj] LT",
                nextWeek: "dddd LT",
                lastDay: "[Vakar] LT",
                lastWeek: "[Praėjusį] dddd LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "po %s",
                past: "prieš %s",
                s: t,
                m: n,
                mm: o,
                h: n,
                hh: o,
                d: n,
                dd: o,
                M: n,
                MM: o,
                y: n,
                yy: o
            },
            ordinalParse: /\d{1,2}-oji/,
            ordinal: function (e) {
                return e + "-oji"
            },
            week: {dow: 1, doy: 4}
        });
        return s
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n) {
            return n ? t % 10 === 1 && t % 100 !== 11 ? e[2] : e[3] : t % 10 === 1 && t % 100 !== 11 ? e[0] : e[1]
        }

        function n(e, n, a) {
            return e + " " + t(o[a], e, n)
        }

        function a(e, n, a) {
            return t(o[a], e, n)
        }

        function i(e, t) {
            return t ? "dažas sekundes" : "dažām sekundēm"
        }

        var o = {
            m: "minūtes_minūtēm_minūte_minūtes".split("_"),
            mm: "minūtes_minūtēm_minūte_minūtes".split("_"),
            h: "stundas_stundām_stunda_stundas".split("_"),
            hh: "stundas_stundām_stunda_stundas".split("_"),
            d: "dienas_dienām_diena_dienas".split("_"),
            dd: "dienas_dienām_diena_dienas".split("_"),
            M: "mēneša_mēnešiem_mēnesis_mēneši".split("_"),
            MM: "mēneša_mēnešiem_mēnesis_mēneši".split("_"),
            y: "gada_gadiem_gads_gadi".split("_"),
            yy: "gada_gadiem_gads_gadi".split("_")
        }, r = e.defineLocale("lv", {
            months: "janvāris_februāris_marts_aprīlis_maijs_jūnijs_jūlijs_augusts_septembris_oktobris_novembris_decembris".split("_"),
            monthsShort: "jan_feb_mar_apr_mai_jūn_jūl_aug_sep_okt_nov_dec".split("_"),
            weekdays: "svētdiena_pirmdiena_otrdiena_trešdiena_ceturtdiena_piektdiena_sestdiena".split("_"),
            weekdaysShort: "Sv_P_O_T_C_Pk_S".split("_"),
            weekdaysMin: "Sv_P_O_T_C_Pk_S".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY.",
                LL: "YYYY. [gada] D. MMMM",
                LLL: "YYYY. [gada] D. MMMM, HH:mm",
                LLLL: "YYYY. [gada] D. MMMM, dddd, HH:mm"
            },
            calendar: {
                sameDay: "[Šodien pulksten] LT",
                nextDay: "[Rīt pulksten] LT",
                nextWeek: "dddd [pulksten] LT",
                lastDay: "[Vakar pulksten] LT",
                lastWeek: "[Pagājušā] dddd [pulksten] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "pēc %s",
                past: "pirms %s",
                s: i,
                m: a,
                mm: n,
                h: a,
                hh: n,
                d: a,
                dd: n,
                M: a,
                MM: n,
                y: a,
                yy: n
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return r
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {
            words: {
                m: ["jedan minut", "jednog minuta"],
                mm: ["minut", "minuta", "minuta"],
                h: ["jedan sat", "jednog sata"],
                hh: ["sat", "sata", "sati"],
                dd: ["dan", "dana", "dana"],
                MM: ["mjesec", "mjeseca", "mjeseci"],
                yy: ["godina", "godine", "godina"]
            }, correctGrammaticalCase: function (e, t) {
                return 1 === e ? t[0] : e >= 2 && e <= 4 ? t[1] : t[2]
            }, translate: function (e, n, a) {
                var i = t.words[a];
                return 1 === a.length ? n ? i[0] : i[1] : e + " " + t.correctGrammaticalCase(e, i)
            }
        }, n = e.defineLocale("me", {
            months: "januar_februar_mart_april_maj_jun_jul_avgust_septembar_oktobar_novembar_decembar".split("_"),
            monthsShort: "jan._feb._mar._apr._maj_jun_jul_avg._sep._okt._nov._dec.".split("_"),
            monthsParseExact: !0,
            weekdays: "nedjelja_ponedjeljak_utorak_srijeda_četvrtak_petak_subota".split("_"),
            weekdaysShort: "ned._pon._uto._sri._čet._pet._sub.".split("_"),
            weekdaysMin: "ne_po_ut_sr_če_pe_su".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd, D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[danas u] LT", nextDay: "[sjutra u] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[u] [nedjelju] [u] LT";
                        case 3:
                            return "[u] [srijedu] [u] LT";
                        case 6:
                            return "[u] [subotu] [u] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[u] dddd [u] LT"
                    }
                }, lastDay: "[juče u] LT", lastWeek: function () {
                    var e = ["[prošle] [nedjelje] [u] LT", "[prošlog] [ponedjeljka] [u] LT", "[prošlog] [utorka] [u] LT", "[prošle] [srijede] [u] LT", "[prošlog] [četvrtka] [u] LT", "[prošlog] [petka] [u] LT", "[prošle] [subote] [u] LT"];
                    return e[this.day()]
                }, sameElse: "L"
            },
            relativeTime: {
                future: "za %s",
                past: "prije %s",
                s: "nekoliko sekundi",
                m: t.translate,
                mm: t.translate,
                h: t.translate,
                hh: t.translate,
                d: "dan",
                dd: t.translate,
                M: "mjesec",
                MM: t.translate,
                y: "godinu",
                yy: t.translate
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("mi", {
            months: "Kohi-tāte_Hui-tanguru_Poutū-te-rangi_Paenga-whāwhā_Haratua_Pipiri_Hōngoingoi_Here-turi-kōkā_Mahuru_Whiringa-ā-nuku_Whiringa-ā-rangi_Hakihea".split("_"),
            monthsShort: "Kohi_Hui_Pou_Pae_Hara_Pipi_Hōngoi_Here_Mahu_Whi-nu_Whi-ra_Haki".split("_"),
            monthsRegex: /(?:['a-z\u0101\u014D\u016B]+\-?){1,3}/i,
            monthsStrictRegex: /(?:['a-z\u0101\u014D\u016B]+\-?){1,3}/i,
            monthsShortRegex: /(?:['a-z\u0101\u014D\u016B]+\-?){1,3}/i,
            monthsShortStrictRegex: /(?:['a-z\u0101\u014D\u016B]+\-?){1,2}/i,
            weekdays: "Rātapu_Mane_Tūrei_Wenerei_Tāite_Paraire_Hātarei".split("_"),
            weekdaysShort: "Ta_Ma_Tū_We_Tāi_Pa_Hā".split("_"),
            weekdaysMin: "Ta_Ma_Tū_We_Tāi_Pa_Hā".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY [i] HH:mm",
                LLLL: "dddd, D MMMM YYYY [i] HH:mm"
            },
            calendar: {
                sameDay: "[i teie mahana, i] LT",
                nextDay: "[apopo i] LT",
                nextWeek: "dddd [i] LT",
                lastDay: "[inanahi i] LT",
                lastWeek: "dddd [whakamutunga i] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "i roto i %s",
                past: "%s i mua",
                s: "te hēkona ruarua",
                m: "he meneti",
                mm: "%d meneti",
                h: "te haora",
                hh: "%d haora",
                d: "he ra",
                dd: "%d ra",
                M: "he marama",
                MM: "%d marama",
                y: "he tau",
                yy: "%d tau"
            },
            ordinalParse: /\d{1,2}º/,
            ordinal: "%dº",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("mk", {
            months: "јануари_февруари_март_април_мај_јуни_јули_август_септември_октомври_ноември_декември".split("_"),
            monthsShort: "јан_фев_мар_апр_мај_јун_јул_авг_сеп_окт_ное_дек".split("_"),
            weekdays: "недела_понеделник_вторник_среда_четврток_петок_сабота".split("_"),
            weekdaysShort: "нед_пон_вто_сре_чет_пет_саб".split("_"),
            weekdaysMin: "нe_пo_вт_ср_че_пе_сa".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "D.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY H:mm",
                LLLL: "dddd, D MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[Денес во] LT",
                nextDay: "[Утре во] LT",
                nextWeek: "[Во] dddd [во] LT",
                lastDay: "[Вчера во] LT",
                lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                        case 3:
                        case 6:
                            return "[Изминатата] dddd [во] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[Изминатиот] dddd [во] LT"
                    }
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "после %s",
                past: "пред %s",
                s: "неколку секунди",
                m: "минута",
                mm: "%d минути",
                h: "час",
                hh: "%d часа",
                d: "ден",
                dd: "%d дена",
                M: "месец",
                MM: "%d месеци",
                y: "година",
                yy: "%d години"
            },
            ordinalParse: /\d{1,2}-(ев|ен|ти|ви|ри|ми)/,
            ordinal: function (e) {
                var t = e % 10, n = e % 100;
                return 0 === e ? e + "-ев" : 0 === n ? e + "-ен" : n > 10 && n < 20 ? e + "-ти" : 1 === t ? e + "-ви" : 2 === t ? e + "-ри" : 7 === t || 8 === t ? e + "-ми" : e + "-ти"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ml", {
            months: "ജനുവരി_ഫെബ്രുവരി_മാർച്ച്_ഏപ്രിൽ_മേയ്_ജൂൺ_ജൂലൈ_ഓഗസ്റ്റ്_സെപ്റ്റംബർ_ഒക്ടോബർ_നവംബർ_ഡിസംബർ".split("_"),
            monthsShort: "ജനു._ഫെബ്രു._മാർ._ഏപ്രി._മേയ്_ജൂൺ_ജൂലൈ._ഓഗ._സെപ്റ്റ._ഒക്ടോ._നവം._ഡിസം.".split("_"),
            monthsParseExact: !0,
            weekdays: "ഞായറാഴ്ച_തിങ്കളാഴ്ച_ചൊവ്വാഴ്ച_ബുധനാഴ്ച_വ്യാഴാഴ്ച_വെള്ളിയാഴ്ച_ശനിയാഴ്ച".split("_"),
            weekdaysShort: "ഞായർ_തിങ്കൾ_ചൊവ്വ_ബുധൻ_വ്യാഴം_വെള്ളി_ശനി".split("_"),
            weekdaysMin: "ഞാ_തി_ചൊ_ബു_വ്യാ_വെ_ശ".split("_"),
            longDateFormat: {
                LT: "A h:mm -നു",
                LTS: "A h:mm:ss -നു",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, A h:mm -നു",
                LLLL: "dddd, D MMMM YYYY, A h:mm -നു"
            },
            calendar: {
                sameDay: "[ഇന്ന്] LT",
                nextDay: "[നാളെ] LT",
                nextWeek: "dddd, LT",
                lastDay: "[ഇന്നലെ] LT",
                lastWeek: "[കഴിഞ്ഞ] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s കഴിഞ്ഞ്",
                past: "%s മുൻപ്",
                s: "അൽപ നിമിഷങ്ങൾ",
                m: "ഒരു മിനിറ്റ്",
                mm: "%d മിനിറ്റ്",
                h: "ഒരു മണിക്കൂർ",
                hh: "%d മണിക്കൂർ",
                d: "ഒരു ദിവസം",
                dd: "%d ദിവസം",
                M: "ഒരു മാസം",
                MM: "%d മാസം",
                y: "ഒരു വർഷം",
                yy: "%d വർഷം"
            },
            meridiemParse: /രാത്രി|രാവിലെ|ഉച്ച കഴിഞ്ഞ്|വൈകുന്നേരം|രാത്രി/i,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "രാത്രി" === t && e >= 4 || "ഉച്ച കഴിഞ്ഞ്" === t || "വൈകുന്നേരം" === t ? e + 12 : e
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "രാത്രി" : e < 12 ? "രാവിലെ" : e < 17 ? "ഉച്ച കഴിഞ്ഞ്" : e < 20 ? "വൈകുന്നേരം" : "രാത്രി"
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = "";
            if (t)switch (n) {
                case"s":
                    i = "काही सेकंद";
                    break;
                case"m":
                    i = "एक मिनिट";
                    break;
                case"mm":
                    i = "%d मिनिटे";
                    break;
                case"h":
                    i = "एक तास";
                    break;
                case"hh":
                    i = "%d तास";
                    break;
                case"d":
                    i = "एक दिवस";
                    break;
                case"dd":
                    i = "%d दिवस";
                    break;
                case"M":
                    i = "एक महिना";
                    break;
                case"MM":
                    i = "%d महिने";
                    break;
                case"y":
                    i = "एक वर्ष";
                    break;
                case"yy":
                    i = "%d वर्षे"
            } else switch (n) {
                case"s":
                    i = "काही सेकंदां";
                    break;
                case"m":
                    i = "एका मिनिटा";
                    break;
                case"mm":
                    i = "%d मिनिटां";
                    break;
                case"h":
                    i = "एका तासा";
                    break;
                case"hh":
                    i = "%d तासां";
                    break;
                case"d":
                    i = "एका दिवसा";
                    break;
                case"dd":
                    i = "%d दिवसां";
                    break;
                case"M":
                    i = "एका महिन्या";
                    break;
                case"MM":
                    i = "%d महिन्यां";
                    break;
                case"y":
                    i = "एका वर्षा";
                    break;
                case"yy":
                    i = "%d वर्षां"
            }
            return i.replace(/%d/i, e)
        }

        var n = {1: "१", 2: "२", 3: "३", 4: "४", 5: "५", 6: "६", 7: "७", 8: "८", 9: "९", 0: "०"}, a = {
            "१": "1",
            "२": "2",
            "३": "3",
            "४": "4",
            "५": "5",
            "६": "6",
            "७": "7",
            "८": "8",
            "९": "9",
            "०": "0"
        }, i = e.defineLocale("mr", {
            months: "जानेवारी_फेब्रुवारी_मार्च_एप्रिल_मे_जून_जुलै_ऑगस्ट_सप्टेंबर_ऑक्टोबर_नोव्हेंबर_डिसेंबर".split("_"),
            monthsShort: "जाने._फेब्रु._मार्च._एप्रि._मे._जून._जुलै._ऑग._सप्टें._ऑक्टो._नोव्हें._डिसें.".split("_"),
            monthsParseExact: !0,
            weekdays: "रविवार_सोमवार_मंगळवार_बुधवार_गुरूवार_शुक्रवार_शनिवार".split("_"),
            weekdaysShort: "रवि_सोम_मंगळ_बुध_गुरू_शुक्र_शनि".split("_"),
            weekdaysMin: "र_सो_मं_बु_गु_शु_श".split("_"),
            longDateFormat: {
                LT: "A h:mm वाजता",
                LTS: "A h:mm:ss वाजता",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, A h:mm वाजता",
                LLLL: "dddd, D MMMM YYYY, A h:mm वाजता"
            },
            calendar: {
                sameDay: "[आज] LT",
                nextDay: "[उद्या] LT",
                nextWeek: "dddd, LT",
                lastDay: "[काल] LT",
                lastWeek: "[मागील] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%sमध्ये",
                past: "%sपूर्वी",
                s: t,
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: t,
                dd: t,
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            preparse: function (e) {
                return e.replace(/[१२३४५६७८९०]/g, function (e) {
                    return a[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return n[e]
                })
            },
            meridiemParse: /रात्री|सकाळी|दुपारी|सायंकाळी/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "रात्री" === t ? e < 4 ? e : e + 12 : "सकाळी" === t ? e : "दुपारी" === t ? e >= 10 ? e : e + 12 : "सायंकाळी" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "रात्री" : e < 10 ? "सकाळी" : e < 17 ? "दुपारी" : e < 20 ? "सायंकाळी" : "रात्री"
            },
            week: {dow: 0, doy: 6}
        });
        return i
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ms", {
            months: "Januari_Februari_Mac_April_Mei_Jun_Julai_Ogos_September_Oktober_November_Disember".split("_"),
            monthsShort: "Jan_Feb_Mac_Apr_Mei_Jun_Jul_Ogs_Sep_Okt_Nov_Dis".split("_"),
            weekdays: "Ahad_Isnin_Selasa_Rabu_Khamis_Jumaat_Sabtu".split("_"),
            weekdaysShort: "Ahd_Isn_Sel_Rab_Kha_Jum_Sab".split("_"),
            weekdaysMin: "Ah_Is_Sl_Rb_Km_Jm_Sb".split("_"),
            longDateFormat: {
                LT: "HH.mm",
                LTS: "HH.mm.ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY [pukul] HH.mm",
                LLLL: "dddd, D MMMM YYYY [pukul] HH.mm"
            },
            meridiemParse: /pagi|tengahari|petang|malam/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "pagi" === t ? e : "tengahari" === t ? e >= 11 ? e : e + 12 : "petang" === t || "malam" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 11 ? "pagi" : e < 15 ? "tengahari" : e < 19 ? "petang" : "malam"
            },
            calendar: {
                sameDay: "[Hari ini pukul] LT",
                nextDay: "[Esok pukul] LT",
                nextWeek: "dddd [pukul] LT",
                lastDay: "[Kelmarin pukul] LT",
                lastWeek: "dddd [lepas pukul] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "dalam %s",
                past: "%s yang lepas",
                s: "beberapa saat",
                m: "seminit",
                mm: "%d minit",
                h: "sejam",
                hh: "%d jam",
                d: "sehari",
                dd: "%d hari",
                M: "sebulan",
                MM: "%d bulan",
                y: "setahun",
                yy: "%d tahun"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ms-my", {
            months: "Januari_Februari_Mac_April_Mei_Jun_Julai_Ogos_September_Oktober_November_Disember".split("_"),
            monthsShort: "Jan_Feb_Mac_Apr_Mei_Jun_Jul_Ogs_Sep_Okt_Nov_Dis".split("_"),
            weekdays: "Ahad_Isnin_Selasa_Rabu_Khamis_Jumaat_Sabtu".split("_"),
            weekdaysShort: "Ahd_Isn_Sel_Rab_Kha_Jum_Sab".split("_"),
            weekdaysMin: "Ah_Is_Sl_Rb_Km_Jm_Sb".split("_"),
            longDateFormat: {
                LT: "HH.mm",
                LTS: "HH.mm.ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY [pukul] HH.mm",
                LLLL: "dddd, D MMMM YYYY [pukul] HH.mm"
            },
            meridiemParse: /pagi|tengahari|petang|malam/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "pagi" === t ? e : "tengahari" === t ? e >= 11 ? e : e + 12 : "petang" === t || "malam" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 11 ? "pagi" : e < 15 ? "tengahari" : e < 19 ? "petang" : "malam"
            },
            calendar: {
                sameDay: "[Hari ini pukul] LT",
                nextDay: "[Esok pukul] LT",
                nextWeek: "dddd [pukul] LT",
                lastDay: "[Kelmarin pukul] LT",
                lastWeek: "dddd [lepas pukul] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "dalam %s",
                past: "%s yang lepas",
                s: "beberapa saat",
                m: "seminit",
                mm: "%d minit",
                h: "sejam",
                hh: "%d jam",
                d: "sehari",
                dd: "%d hari",
                M: "sebulan",
                MM: "%d bulan",
                y: "setahun",
                yy: "%d tahun"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "၁", 2: "၂", 3: "၃", 4: "၄", 5: "၅", 6: "၆", 7: "၇", 8: "၈", 9: "၉", 0: "၀"}, n = {
            "၁": "1",
            "၂": "2",
            "၃": "3",
            "၄": "4",
            "၅": "5",
            "၆": "6",
            "၇": "7",
            "၈": "8",
            "၉": "9",
            "၀": "0"
        }, a = e.defineLocale("my", {
            months: "ဇန်နဝါရီ_ဖေဖော်ဝါရီ_မတ်_ဧပြီ_မေ_ဇွန်_ဇူလိုင်_သြဂုတ်_စက်တင်ဘာ_အောက်တိုဘာ_နိုဝင်ဘာ_ဒီဇင်ဘာ".split("_"),
            monthsShort: "ဇန်_ဖေ_မတ်_ပြီ_မေ_ဇွန်_လိုင်_သြ_စက်_အောက်_နို_ဒီ".split("_"),
            weekdays: "တနင်္ဂနွေ_တနင်္လာ_အင်္ဂါ_ဗုဒ္ဓဟူး_ကြာသပတေး_သောကြာ_စနေ".split("_"),
            weekdaysShort: "နွေ_လာ_ဂါ_ဟူး_ကြာ_သော_နေ".split("_"),
            weekdaysMin: "နွေ_လာ_ဂါ_ဟူး_ကြာ_သော_နေ".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[ယနေ.] LT [မှာ]",
                nextDay: "[မနက်ဖြန်] LT [မှာ]",
                nextWeek: "dddd LT [မှာ]",
                lastDay: "[မနေ.က] LT [မှာ]",
                lastWeek: "[ပြီးခဲ့သော] dddd LT [မှာ]",
                sameElse: "L"
            },
            relativeTime: {
                future: "လာမည့် %s မှာ",
                past: "လွန်ခဲ့သော %s က",
                s: "စက္ကန်.အနည်းငယ်",
                m: "တစ်မိနစ်",
                mm: "%d မိနစ်",
                h: "တစ်နာရီ",
                hh: "%d နာရီ",
                d: "တစ်ရက်",
                dd: "%d ရက်",
                M: "တစ်လ",
                MM: "%d လ",
                y: "တစ်နှစ်",
                yy: "%d နှစ်"
            },
            preparse: function (e) {
                return e.replace(/[၁၂၃၄၅၆၇၈၉၀]/g, function (e) {
                    return n[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                })
            },
            week: {dow: 1, doy: 4}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("nb", {
            months: "januar_februar_mars_april_mai_juni_juli_august_september_oktober_november_desember".split("_"),
            monthsShort: "jan._feb._mars_april_mai_juni_juli_aug._sep._okt._nov._des.".split("_"),
            monthsParseExact: !0,
            weekdays: "søndag_mandag_tirsdag_onsdag_torsdag_fredag_lørdag".split("_"),
            weekdaysShort: "sø._ma._ti._on._to._fr._lø.".split("_"),
            weekdaysMin: "sø_ma_ti_on_to_fr_lø".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY [kl.] HH:mm",
                LLLL: "dddd D. MMMM YYYY [kl.] HH:mm"
            },
            calendar: {
                sameDay: "[i dag kl.] LT",
                nextDay: "[i morgen kl.] LT",
                nextWeek: "dddd [kl.] LT",
                lastDay: "[i går kl.] LT",
                lastWeek: "[forrige] dddd [kl.] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "om %s",
                past: "%s siden",
                s: "noen sekunder",
                m: "ett minutt",
                mm: "%d minutter",
                h: "en time",
                hh: "%d timer",
                d: "en dag",
                dd: "%d dager",
                M: "en måned",
                MM: "%d måneder",
                y: "ett år",
                yy: "%d år"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "१", 2: "२", 3: "३", 4: "४", 5: "५", 6: "६", 7: "७", 8: "८", 9: "९", 0: "०"}, n = {
            "१": "1",
            "२": "2",
            "३": "3",
            "४": "4",
            "५": "5",
            "६": "6",
            "७": "7",
            "८": "8",
            "९": "9",
            "०": "0"
        }, a = e.defineLocale("ne", {
            months: "जनवरी_फेब्रुवरी_मार्च_अप्रिल_मई_जुन_जुलाई_अगष्ट_सेप्टेम्बर_अक्टोबर_नोभेम्बर_डिसेम्बर".split("_"),
            monthsShort: "जन._फेब्रु._मार्च_अप्रि._मई_जुन_जुलाई._अग._सेप्ट._अक्टो._नोभे._डिसे.".split("_"),
            monthsParseExact: !0,
            weekdays: "आइतबार_सोमबार_मङ्गलबार_बुधबार_बिहिबार_शुक्रबार_शनिबार".split("_"),
            weekdaysShort: "आइत._सोम._मङ्गल._बुध._बिहि._शुक्र._शनि.".split("_"),
            weekdaysMin: "आ._सो._मं._बु._बि._शु._श.".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "Aको h:mm बजे",
                LTS: "Aको h:mm:ss बजे",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, Aको h:mm बजे",
                LLLL: "dddd, D MMMM YYYY, Aको h:mm बजे"
            },
            preparse: function (e) {
                return e.replace(/[१२३४५६७८९०]/g, function (e) {
                    return n[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                })
            },
            meridiemParse: /राति|बिहान|दिउँसो|साँझ/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "राति" === t ? e < 4 ? e : e + 12 : "बिहान" === t ? e : "दिउँसो" === t ? e >= 10 ? e : e + 12 : "साँझ" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 3 ? "राति" : e < 12 ? "बिहान" : e < 16 ? "दिउँसो" : e < 20 ? "साँझ" : "राति"
            },
            calendar: {
                sameDay: "[आज] LT",
                nextDay: "[भोलि] LT",
                nextWeek: "[आउँदो] dddd[,] LT",
                lastDay: "[हिजो] LT",
                lastWeek: "[गएको] dddd[,] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%sमा",
                past: "%s अगाडि",
                s: "केही क्षण",
                m: "एक मिनेट",
                mm: "%d मिनेट",
                h: "एक घण्टा",
                hh: "%d घण्टा",
                d: "एक दिन",
                dd: "%d दिन",
                M: "एक महिना",
                MM: "%d महिना",
                y: "एक बर्ष",
                yy: "%d बर्ष"
            },
            week: {dow: 0, doy: 6}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = "jan._feb._mrt._apr._mei_jun._jul._aug._sep._okt._nov._dec.".split("_"), n = "jan_feb_mrt_apr_mei_jun_jul_aug_sep_okt_nov_dec".split("_"), a = [/^jan/i, /^feb/i, /^maart|mrt.?$/i, /^apr/i, /^mei$/i, /^jun[i.]?$/i, /^jul[i.]?$/i, /^aug/i, /^sep/i, /^okt/i, /^nov/i, /^dec/i], i = /^(januari|februari|maart|april|mei|april|ju[nl]i|augustus|september|oktober|november|december|jan\.?|feb\.?|mrt\.?|apr\.?|ju[nl]\.?|aug\.?|sep\.?|okt\.?|nov\.?|dec\.?)/i, o = e.defineLocale("nl", {
            months: "januari_februari_maart_april_mei_juni_juli_augustus_september_oktober_november_december".split("_"),
            monthsShort: function (e, a) {
                return /-MMM-/.test(a) ? n[e.month()] : t[e.month()]
            },
            monthsRegex: i,
            monthsShortRegex: i,
            monthsStrictRegex: /^(januari|februari|maart|mei|ju[nl]i|april|augustus|september|oktober|november|december)/i,
            monthsShortStrictRegex: /^(jan\.?|feb\.?|mrt\.?|apr\.?|mei|ju[nl]\.?|aug\.?|sep\.?|okt\.?|nov\.?|dec\.?)/i,
            monthsParse: a,
            longMonthsParse: a,
            shortMonthsParse: a,
            weekdays: "zondag_maandag_dinsdag_woensdag_donderdag_vrijdag_zaterdag".split("_"),
            weekdaysShort: "zo._ma._di._wo._do._vr._za.".split("_"),
            weekdaysMin: "Zo_Ma_Di_Wo_Do_Vr_Za".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD-MM-YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[vandaag om] LT",
                nextDay: "[morgen om] LT",
                nextWeek: "dddd [om] LT",
                lastDay: "[gisteren om] LT",
                lastWeek: "[afgelopen] dddd [om] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "over %s",
                past: "%s geleden",
                s: "een paar seconden",
                m: "één minuut",
                mm: "%d minuten",
                h: "één uur",
                hh: "%d uur",
                d: "één dag",
                dd: "%d dagen",
                M: "één maand",
                MM: "%d maanden",
                y: "één jaar",
                yy: "%d jaar"
            },
            ordinalParse: /\d{1,2}(ste|de)/,
            ordinal: function (e) {
                return e + (1 === e || 8 === e || e >= 20 ? "ste" : "de")
            },
            week: {dow: 1, doy: 4}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = "jan._feb._mrt._apr._mei_jun._jul._aug._sep._okt._nov._dec.".split("_"), n = "jan_feb_mrt_apr_mei_jun_jul_aug_sep_okt_nov_dec".split("_"), a = [/^jan/i, /^feb/i, /^maart|mrt.?$/i, /^apr/i, /^mei$/i, /^jun[i.]?$/i, /^jul[i.]?$/i, /^aug/i, /^sep/i, /^okt/i, /^nov/i, /^dec/i], i = /^(januari|februari|maart|april|mei|april|ju[nl]i|augustus|september|oktober|november|december|jan\.?|feb\.?|mrt\.?|apr\.?|ju[nl]\.?|aug\.?|sep\.?|okt\.?|nov\.?|dec\.?)/i, o = e.defineLocale("nl-be", {
            months: "januari_februari_maart_april_mei_juni_juli_augustus_september_oktober_november_december".split("_"),
            monthsShort: function (e, a) {
                return /-MMM-/.test(a) ? n[e.month()] : t[e.month()]
            },
            monthsRegex: i,
            monthsShortRegex: i,
            monthsStrictRegex: /^(januari|februari|maart|mei|ju[nl]i|april|augustus|september|oktober|november|december)/i,
            monthsShortStrictRegex: /^(jan\.?|feb\.?|mrt\.?|apr\.?|mei|ju[nl]\.?|aug\.?|sep\.?|okt\.?|nov\.?|dec\.?)/i,
            monthsParse: a,
            longMonthsParse: a,
            shortMonthsParse: a,
            weekdays: "zondag_maandag_dinsdag_woensdag_donderdag_vrijdag_zaterdag".split("_"),
            weekdaysShort: "zo._ma._di._wo._do._vr._za.".split("_"),
            weekdaysMin: "Zo_Ma_Di_Wo_Do_Vr_Za".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[vandaag om] LT",
                nextDay: "[morgen om] LT",
                nextWeek: "dddd [om] LT",
                lastDay: "[gisteren om] LT",
                lastWeek: "[afgelopen] dddd [om] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "over %s",
                past: "%s geleden",
                s: "een paar seconden",
                m: "één minuut",
                mm: "%d minuten",
                h: "één uur",
                hh: "%d uur",
                d: "één dag",
                dd: "%d dagen",
                M: "één maand",
                MM: "%d maanden",
                y: "één jaar",
                yy: "%d jaar"
            },
            ordinalParse: /\d{1,2}(ste|de)/,
            ordinal: function (e) {
                return e + (1 === e || 8 === e || e >= 20 ? "ste" : "de")
            },
            week: {dow: 1, doy: 4}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("nn", {
            months: "januar_februar_mars_april_mai_juni_juli_august_september_oktober_november_desember".split("_"),
            monthsShort: "jan_feb_mar_apr_mai_jun_jul_aug_sep_okt_nov_des".split("_"),
            weekdays: "sundag_måndag_tysdag_onsdag_torsdag_fredag_laurdag".split("_"),
            weekdaysShort: "sun_mån_tys_ons_tor_fre_lau".split("_"),
            weekdaysMin: "su_må_ty_on_to_fr_lø".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY [kl.] H:mm",
                LLLL: "dddd D. MMMM YYYY [kl.] HH:mm"
            },
            calendar: {
                sameDay: "[I dag klokka] LT",
                nextDay: "[I morgon klokka] LT",
                nextWeek: "dddd [klokka] LT",
                lastDay: "[I går klokka] LT",
                lastWeek: "[Føregåande] dddd [klokka] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "om %s",
                past: "%s sidan",
                s: "nokre sekund",
                m: "eit minutt",
                mm: "%d minutt",
                h: "ein time",
                hh: "%d timar",
                d: "ein dag",
                dd: "%d dagar",
                M: "ein månad",
                MM: "%d månader",
                y: "eit år",
                yy: "%d år"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "੧", 2: "੨", 3: "੩", 4: "੪", 5: "੫", 6: "੬", 7: "੭", 8: "੮", 9: "੯", 0: "੦"}, n = {
            "੧": "1",
            "੨": "2",
            "੩": "3",
            "੪": "4",
            "੫": "5",
            "੬": "6",
            "੭": "7",
            "੮": "8",
            "੯": "9",
            "੦": "0"
        }, a = e.defineLocale("pa-in", {
            months: "ਜਨਵਰੀ_ਫ਼ਰਵਰੀ_ਮਾਰਚ_ਅਪ੍ਰੈਲ_ਮਈ_ਜੂਨ_ਜੁਲਾਈ_ਅਗਸਤ_ਸਤੰਬਰ_ਅਕਤੂਬਰ_ਨਵੰਬਰ_ਦਸੰਬਰ".split("_"),
            monthsShort: "ਜਨਵਰੀ_ਫ਼ਰਵਰੀ_ਮਾਰਚ_ਅਪ੍ਰੈਲ_ਮਈ_ਜੂਨ_ਜੁਲਾਈ_ਅਗਸਤ_ਸਤੰਬਰ_ਅਕਤੂਬਰ_ਨਵੰਬਰ_ਦਸੰਬਰ".split("_"),
            weekdays: "ਐਤਵਾਰ_ਸੋਮਵਾਰ_ਮੰਗਲਵਾਰ_ਬੁਧਵਾਰ_ਵੀਰਵਾਰ_ਸ਼ੁੱਕਰਵਾਰ_ਸ਼ਨੀਚਰਵਾਰ".split("_"),
            weekdaysShort: "ਐਤ_ਸੋਮ_ਮੰਗਲ_ਬੁਧ_ਵੀਰ_ਸ਼ੁਕਰ_ਸ਼ਨੀ".split("_"),
            weekdaysMin: "ਐਤ_ਸੋਮ_ਮੰਗਲ_ਬੁਧ_ਵੀਰ_ਸ਼ੁਕਰ_ਸ਼ਨੀ".split("_"),
            longDateFormat: {
                LT: "A h:mm ਵਜੇ",
                LTS: "A h:mm:ss ਵਜੇ",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, A h:mm ਵਜੇ",
                LLLL: "dddd, D MMMM YYYY, A h:mm ਵਜੇ"
            },
            calendar: {
                sameDay: "[ਅਜ] LT",
                nextDay: "[ਕਲ] LT",
                nextWeek: "dddd, LT",
                lastDay: "[ਕਲ] LT",
                lastWeek: "[ਪਿਛਲੇ] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s ਵਿੱਚ",
                past: "%s ਪਿਛਲੇ",
                s: "ਕੁਝ ਸਕਿੰਟ",
                m: "ਇਕ ਮਿੰਟ",
                mm: "%d ਮਿੰਟ",
                h: "ਇੱਕ ਘੰਟਾ",
                hh: "%d ਘੰਟੇ",
                d: "ਇੱਕ ਦਿਨ",
                dd: "%d ਦਿਨ",
                M: "ਇੱਕ ਮਹੀਨਾ",
                MM: "%d ਮਹੀਨੇ",
                y: "ਇੱਕ ਸਾਲ",
                yy: "%d ਸਾਲ"
            },
            preparse: function (e) {
                return e.replace(/[੧੨੩੪੫੬੭੮੯੦]/g, function (e) {
                    return n[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                })
            },
            meridiemParse: /ਰਾਤ|ਸਵੇਰ|ਦੁਪਹਿਰ|ਸ਼ਾਮ/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "ਰਾਤ" === t ? e < 4 ? e : e + 12 : "ਸਵੇਰ" === t ? e : "ਦੁਪਹਿਰ" === t ? e >= 10 ? e : e + 12 : "ਸ਼ਾਮ" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "ਰਾਤ" : e < 10 ? "ਸਵੇਰ" : e < 17 ? "ਦੁਪਹਿਰ" : e < 20 ? "ਸ਼ਾਮ" : "ਰਾਤ"
            },
            week: {dow: 0, doy: 6}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e) {
            return e % 10 < 5 && e % 10 > 1 && ~~(e / 10) % 10 !== 1
        }

        function n(e, n, a) {
            var i = e + " ";
            switch (a) {
                case"m":
                    return n ? "minuta" : "minutę";
                case"mm":
                    return i + (t(e) ? "minuty" : "minut");
                case"h":
                    return n ? "godzina" : "godzinę";
                case"hh":
                    return i + (t(e) ? "godziny" : "godzin");
                case"MM":
                    return i + (t(e) ? "miesiące" : "miesięcy");
                case"yy":
                    return i + (t(e) ? "lata" : "lat")
            }
        }

        var a = "styczeń_luty_marzec_kwiecień_maj_czerwiec_lipiec_sierpień_wrzesień_październik_listopad_grudzień".split("_"), i = "stycznia_lutego_marca_kwietnia_maja_czerwca_lipca_sierpnia_września_października_listopada_grudnia".split("_"), o = e.defineLocale("pl", {
            months: function (e, t) {
                return "" === t ? "(" + i[e.month()] + "|" + a[e.month()] + ")" : /D MMMM/.test(t) ? i[e.month()] : a[e.month()]
            },
            monthsShort: "sty_lut_mar_kwi_maj_cze_lip_sie_wrz_paź_lis_gru".split("_"),
            weekdays: "niedziela_poniedziałek_wtorek_środa_czwartek_piątek_sobota".split("_"),
            weekdaysShort: "ndz_pon_wt_śr_czw_pt_sob".split("_"),
            weekdaysMin: "Nd_Pn_Wt_Śr_Cz_Pt_So".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Dziś o] LT",
                nextDay: "[Jutro o] LT",
                nextWeek: "[W] dddd [o] LT",
                lastDay: "[Wczoraj o] LT",
                lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[W zeszłą niedzielę o] LT";
                        case 3:
                            return "[W zeszłą środę o] LT";
                        case 6:
                            return "[W zeszłą sobotę o] LT";
                        default:
                            return "[W zeszły] dddd [o] LT"
                    }
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "za %s",
                past: "%s temu",
                s: "kilka sekund",
                m: n,
                mm: n,
                h: n,
                hh: n,
                d: "1 dzień",
                dd: "%d dni",
                M: "miesiąc",
                MM: n,
                y: "rok",
                yy: n
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("pt", {
            months: "Janeiro_Fevereiro_Março_Abril_Maio_Junho_Julho_Agosto_Setembro_Outubro_Novembro_Dezembro".split("_"),
            monthsShort: "Jan_Fev_Mar_Abr_Mai_Jun_Jul_Ago_Set_Out_Nov_Dez".split("_"),
            weekdays: "Domingo_Segunda-Feira_Terça-Feira_Quarta-Feira_Quinta-Feira_Sexta-Feira_Sábado".split("_"),
            weekdaysShort: "Dom_Seg_Ter_Qua_Qui_Sex_Sáb".split("_"),
            weekdaysMin: "Dom_2ª_3ª_4ª_5ª_6ª_Sáb".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D [de] MMMM [de] YYYY",
                LLL: "D [de] MMMM [de] YYYY HH:mm",
                LLLL: "dddd, D [de] MMMM [de] YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Hoje às] LT",
                nextDay: "[Amanhã às] LT",
                nextWeek: "dddd [às] LT",
                lastDay: "[Ontem às] LT",
                lastWeek: function () {
                    return 0 === this.day() || 6 === this.day() ? "[Último] dddd [às] LT" : "[Última] dddd [às] LT"
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "em %s",
                past: "há %s",
                s: "segundos",
                m: "um minuto",
                mm: "%d minutos",
                h: "uma hora",
                hh: "%d horas",
                d: "um dia",
                dd: "%d dias",
                M: "um mês",
                MM: "%d meses",
                y: "um ano",
                yy: "%d anos"
            },
            ordinalParse: /\d{1,2}º/,
            ordinal: "%dº",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("pt-br", {
            months: "Janeiro_Fevereiro_Março_Abril_Maio_Junho_Julho_Agosto_Setembro_Outubro_Novembro_Dezembro".split("_"),
            monthsShort: "Jan_Fev_Mar_Abr_Mai_Jun_Jul_Ago_Set_Out_Nov_Dez".split("_"),
            weekdays: "Domingo_Segunda-feira_Terça-feira_Quarta-feira_Quinta-feira_Sexta-feira_Sábado".split("_"),
            weekdaysShort: "Dom_Seg_Ter_Qua_Qui_Sex_Sáb".split("_"),
            weekdaysMin: "Dom_2ª_3ª_4ª_5ª_6ª_Sáb".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D [de] MMMM [de] YYYY",
                LLL: "D [de] MMMM [de] YYYY [às] HH:mm",
                LLLL: "dddd, D [de] MMMM [de] YYYY [às] HH:mm"
            },
            calendar: {
                sameDay: "[Hoje às] LT",
                nextDay: "[Amanhã às] LT",
                nextWeek: "dddd [às] LT",
                lastDay: "[Ontem às] LT",
                lastWeek: function () {
                    return 0 === this.day() || 6 === this.day() ? "[Último] dddd [às] LT" : "[Última] dddd [às] LT"
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "em %s",
                past: "%s atrás",
                s: "poucos segundos",
                m: "um minuto",
                mm: "%d minutos",
                h: "uma hora",
                hh: "%d horas",
                d: "um dia",
                dd: "%d dias",
                M: "um mês",
                MM: "%d meses",
                y: "um ano",
                yy: "%d anos"
            },
            ordinalParse: /\d{1,2}º/,
            ordinal: "%dº"
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n) {
            var a = {mm: "minute", hh: "ore", dd: "zile", MM: "luni", yy: "ani"}, i = " ";
            return (e % 100 >= 20 || e >= 100 && e % 100 === 0) && (i = " de "), e + i + a[n]
        }

        var n = e.defineLocale("ro", {
            months: "ianuarie_februarie_martie_aprilie_mai_iunie_iulie_august_septembrie_octombrie_noiembrie_decembrie".split("_"),
            monthsShort: "ian._febr._mart._apr._mai_iun._iul._aug._sept._oct._nov._dec.".split("_"),
            monthsParseExact: !0,
            weekdays: "duminică_luni_marți_miercuri_joi_vineri_sâmbătă".split("_"),
            weekdaysShort: "Dum_Lun_Mar_Mie_Joi_Vin_Sâm".split("_"),
            weekdaysMin: "Du_Lu_Ma_Mi_Jo_Vi_Sâ".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY H:mm",
                LLLL: "dddd, D MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[azi la] LT",
                nextDay: "[mâine la] LT",
                nextWeek: "dddd [la] LT",
                lastDay: "[ieri la] LT",
                lastWeek: "[fosta] dddd [la] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "peste %s",
                past: "%s în urmă",
                s: "câteva secunde",
                m: "un minut",
                mm: t,
                h: "o oră",
                hh: t,
                d: "o zi",
                dd: t,
                M: "o lună",
                MM: t,
                y: "un an",
                yy: t
            },
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t) {
            var n = e.split("_");
            return t % 10 === 1 && t % 100 !== 11 ? n[0] : t % 10 >= 2 && t % 10 <= 4 && (t % 100 < 10 || t % 100 >= 20) ? n[1] : n[2]
        }

        function n(e, n, a) {
            var i = {
                mm: n ? "минута_минуты_минут" : "минуту_минуты_минут",
                hh: "час_часа_часов",
                dd: "день_дня_дней",
                MM: "месяц_месяца_месяцев",
                yy: "год_года_лет"
            };
            return "m" === a ? n ? "минута" : "минуту" : e + " " + t(i[a], +e)
        }

        var a = [/^янв/i, /^фев/i, /^мар/i, /^апр/i, /^ма[йя]/i, /^июн/i, /^июл/i, /^авг/i, /^сен/i, /^окт/i, /^ноя/i, /^дек/i], i = e.defineLocale("ru", {
            months: {
                format: "января_февраля_марта_апреля_мая_июня_июля_августа_сентября_октября_ноября_декабря".split("_"),
                standalone: "январь_февраль_март_апрель_май_июнь_июль_август_сентябрь_октябрь_ноябрь_декабрь".split("_")
            },
            monthsShort: {
                format: "янв._февр._мар._апр._мая_июня_июля_авг._сент._окт._нояб._дек.".split("_"),
                standalone: "янв._февр._март_апр._май_июнь_июль_авг._сент._окт._нояб._дек.".split("_")
            },
            weekdays: {
                standalone: "воскресенье_понедельник_вторник_среда_четверг_пятница_суббота".split("_"),
                format: "воскресенье_понедельник_вторник_среду_четверг_пятницу_субботу".split("_"),
                isFormat: /\[ ?[Вв] ?(?:прошлую|следующую|эту)? ?\] ?dddd/
            },
            weekdaysShort: "вс_пн_вт_ср_чт_пт_сб".split("_"),
            weekdaysMin: "вс_пн_вт_ср_чт_пт_сб".split("_"),
            monthsParse: a,
            longMonthsParse: a,
            shortMonthsParse: a,
            monthsRegex: /^(январ[ья]|янв\.?|феврал[ья]|февр?\.?|марта?|мар\.?|апрел[ья]|апр\.?|ма[йя]|июн[ья]|июн\.?|июл[ья]|июл\.?|августа?|авг\.?|сентябр[ья]|сент?\.?|октябр[ья]|окт\.?|ноябр[ья]|нояб?\.?|декабр[ья]|дек\.?)/i,
            monthsShortRegex: /^(январ[ья]|янв\.?|феврал[ья]|февр?\.?|марта?|мар\.?|апрел[ья]|апр\.?|ма[йя]|июн[ья]|июн\.?|июл[ья]|июл\.?|августа?|авг\.?|сентябр[ья]|сент?\.?|октябр[ья]|окт\.?|ноябр[ья]|нояб?\.?|декабр[ья]|дек\.?)/i,
            monthsStrictRegex: /^(январ[яь]|феврал[яь]|марта?|апрел[яь]|ма[яй]|июн[яь]|июл[яь]|августа?|сентябр[яь]|октябр[яь]|ноябр[яь]|декабр[яь])/i,
            monthsShortStrictRegex: /^(янв\.|февр?\.|мар[т.]|апр\.|ма[яй]|июн[ья.]|июл[ья.]|авг\.|сент?\.|окт\.|нояб?\.|дек\.)/i,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY г.",
                LLL: "D MMMM YYYY г., HH:mm",
                LLLL: "dddd, D MMMM YYYY г., HH:mm"
            },
            calendar: {
                sameDay: "[Сегодня в] LT",
                nextDay: "[Завтра в] LT",
                lastDay: "[Вчера в] LT",
                nextWeek: function (e) {
                    if (e.week() === this.week())return 2 === this.day() ? "[Во] dddd [в] LT" : "[В] dddd [в] LT";
                    switch (this.day()) {
                        case 0:
                            return "[В следующее] dddd [в] LT";
                        case 1:
                        case 2:
                        case 4:
                            return "[В следующий] dddd [в] LT";
                        case 3:
                        case 5:
                        case 6:
                            return "[В следующую] dddd [в] LT"
                    }
                },
                lastWeek: function (e) {
                    if (e.week() === this.week())return 2 === this.day() ? "[Во] dddd [в] LT" : "[В] dddd [в] LT";
                    switch (this.day()) {
                        case 0:
                            return "[В прошлое] dddd [в] LT";
                        case 1:
                        case 2:
                        case 4:
                            return "[В прошлый] dddd [в] LT";
                        case 3:
                        case 5:
                        case 6:
                            return "[В прошлую] dddd [в] LT"
                    }
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "через %s",
                past: "%s назад",
                s: "несколько секунд",
                m: n,
                mm: n,
                h: "час",
                hh: n,
                d: "день",
                dd: n,
                M: "месяц",
                MM: n,
                y: "год",
                yy: n
            },
            meridiemParse: /ночи|утра|дня|вечера/i,
            isPM: function (e) {
                return /^(дня|вечера)$/.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "ночи" : e < 12 ? "утра" : e < 17 ? "дня" : "вечера"
            },
            ordinalParse: /\d{1,2}-(й|го|я)/,
            ordinal: function (e, t) {
                switch (t) {
                    case"M":
                    case"d":
                    case"DDD":
                        return e + "-й";
                    case"D":
                        return e + "-го";
                    case"w":
                    case"W":
                        return e + "-я";
                    default:
                        return e
                }
            },
            week: {dow: 1, doy: 7}
        });
        return i
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("se", {
            months: "ođđajagemánnu_guovvamánnu_njukčamánnu_cuoŋománnu_miessemánnu_geassemánnu_suoidnemánnu_borgemánnu_čakčamánnu_golggotmánnu_skábmamánnu_juovlamánnu".split("_"),
            monthsShort: "ođđj_guov_njuk_cuo_mies_geas_suoi_borg_čakč_golg_skáb_juov".split("_"),
            weekdays: "sotnabeaivi_vuossárga_maŋŋebárga_gaskavahkku_duorastat_bearjadat_lávvardat".split("_"),
            weekdaysShort: "sotn_vuos_maŋ_gask_duor_bear_láv".split("_"),
            weekdaysMin: "s_v_m_g_d_b_L".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "MMMM D. [b.] YYYY",
                LLL: "MMMM D. [b.] YYYY [ti.] HH:mm",
                LLLL: "dddd, MMMM D. [b.] YYYY [ti.] HH:mm"
            },
            calendar: {
                sameDay: "[otne ti] LT",
                nextDay: "[ihttin ti] LT",
                nextWeek: "dddd [ti] LT",
                lastDay: "[ikte ti] LT",
                lastWeek: "[ovddit] dddd [ti] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s geažes",
                past: "maŋit %s",
                s: "moadde sekunddat",
                m: "okta minuhta",
                mm: "%d minuhtat",
                h: "okta diimmu",
                hh: "%d diimmut",
                d: "okta beaivi",
                dd: "%d beaivvit",
                M: "okta mánnu",
                MM: "%d mánut",
                y: "okta jahki",
                yy: "%d jagit"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("si", {
            months: "ජනවාරි_පෙබරවාරි_මාර්තු_අප්‍රේල්_මැයි_ජූනි_ජූලි_අගෝස්තු_සැප්තැම්බර්_ඔක්තෝබර්_නොවැම්බර්_දෙසැම්බර්".split("_"),
            monthsShort: "ජන_පෙබ_මාර්_අප්_මැයි_ජූනි_ජූලි_අගෝ_සැප්_ඔක්_නොවැ_දෙසැ".split("_"),
            weekdays: "ඉරිදා_සඳුදා_අඟහරුවාදා_බදාදා_බ්‍රහස්පතින්දා_සිකුරාදා_සෙනසුරාදා".split("_"),
            weekdaysShort: "ඉරි_සඳු_අඟ_බදා_බ්‍රහ_සිකු_සෙන".split("_"),
            weekdaysMin: "ඉ_ස_අ_බ_බ්‍ර_සි_සෙ".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "a h:mm",
                LTS: "a h:mm:ss",
                L: "YYYY/MM/DD",
                LL: "YYYY MMMM D",
                LLL: "YYYY MMMM D, a h:mm",
                LLLL: "YYYY MMMM D [වැනි] dddd, a h:mm:ss"
            },
            calendar: {
                sameDay: "[අද] LT[ට]",
                nextDay: "[හෙට] LT[ට]",
                nextWeek: "dddd LT[ට]",
                lastDay: "[ඊයේ] LT[ට]",
                lastWeek: "[පසුගිය] dddd LT[ට]",
                sameElse: "L"
            },
            relativeTime: {
                future: "%sකින්",
                past: "%sකට පෙර",
                s: "තත්පර කිහිපය",
                m: "මිනිත්තුව",
                mm: "මිනිත්තු %d",
                h: "පැය",
                hh: "පැය %d",
                d: "දිනය",
                dd: "දින %d",
                M: "මාසය",
                MM: "මාස %d",
                y: "වසර",
                yy: "වසර %d"
            },
            ordinalParse: /\d{1,2} වැනි/,
            ordinal: function (e) {
                return e + " වැනි"
            },
            meridiemParse: /පෙර වරු|පස් වරු|පෙ.ව|ප.ව./,
            isPM: function (e) {
                return "ප.ව." === e || "පස් වරු" === e
            },
            meridiem: function (e, t, n) {
                return e > 11 ? n ? "ප.ව." : "පස් වරු" : n ? "පෙ.ව." : "පෙර වරු"
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e) {
            return e > 1 && e < 5
        }

        function n(e, n, a, i) {
            var o = e + " ";
            switch (a) {
                case"s":
                    return n || i ? "pár sekúnd" : "pár sekundami";
                case"m":
                    return n ? "minúta" : i ? "minútu" : "minútou";
                case"mm":
                    return n || i ? o + (t(e) ? "minúty" : "minút") : o + "minútami";
                case"h":
                    return n ? "hodina" : i ? "hodinu" : "hodinou";
                case"hh":
                    return n || i ? o + (t(e) ? "hodiny" : "hodín") : o + "hodinami";
                case"d":
                    return n || i ? "deň" : "dňom";
                case"dd":
                    return n || i ? o + (t(e) ? "dni" : "dní") : o + "dňami";
                case"M":
                    return n || i ? "mesiac" : "mesiacom";
                case"MM":
                    return n || i ? o + (t(e) ? "mesiace" : "mesiacov") : o + "mesiacmi";
                case"y":
                    return n || i ? "rok" : "rokom";
                case"yy":
                    return n || i ? o + (t(e) ? "roky" : "rokov") : o + "rokmi"
            }
        }

        var a = "január_február_marec_apríl_máj_jún_júl_august_september_október_november_december".split("_"), i = "jan_feb_mar_apr_máj_jún_júl_aug_sep_okt_nov_dec".split("_"), o = e.defineLocale("sk", {
            months: a,
            monthsShort: i,
            weekdays: "nedeľa_pondelok_utorok_streda_štvrtok_piatok_sobota".split("_"),
            weekdaysShort: "ne_po_ut_st_št_pi_so".split("_"),
            weekdaysMin: "ne_po_ut_st_št_pi_so".split("_"),
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[dnes o] LT", nextDay: "[zajtra o] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[v nedeľu o] LT";
                        case 1:
                        case 2:
                            return "[v] dddd [o] LT";
                        case 3:
                            return "[v stredu o] LT";
                        case 4:
                            return "[vo štvrtok o] LT";
                        case 5:
                            return "[v piatok o] LT";
                        case 6:
                            return "[v sobotu o] LT"
                    }
                }, lastDay: "[včera o] LT", lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[minulú nedeľu o] LT";
                        case 1:
                        case 2:
                            return "[minulý] dddd [o] LT";
                        case 3:
                            return "[minulú stredu o] LT";
                        case 4:
                        case 5:
                            return "[minulý] dddd [o] LT";
                        case 6:
                            return "[minulú sobotu o] LT"
                    }
                }, sameElse: "L"
            },
            relativeTime: {
                future: "za %s",
                past: "pred %s",
                s: n,
                m: n,
                mm: n,
                h: n,
                hh: n,
                d: n,
                dd: n,
                M: n,
                MM: n,
                y: n,
                yy: n
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = e + " ";
            switch (n) {
                case"s":
                    return t || a ? "nekaj sekund" : "nekaj sekundami";
                case"m":
                    return t ? "ena minuta" : "eno minuto";
                case"mm":
                    return i += 1 === e ? t ? "minuta" : "minuto" : 2 === e ? t || a ? "minuti" : "minutama" : e < 5 ? t || a ? "minute" : "minutami" : t || a ? "minut" : "minutami";
                case"h":
                    return t ? "ena ura" : "eno uro";
                case"hh":
                    return i += 1 === e ? t ? "ura" : "uro" : 2 === e ? t || a ? "uri" : "urama" : e < 5 ? t || a ? "ure" : "urami" : t || a ? "ur" : "urami";
                case"d":
                    return t || a ? "en dan" : "enim dnem";
                case"dd":
                    return i += 1 === e ? t || a ? "dan" : "dnem" : 2 === e ? t || a ? "dni" : "dnevoma" : t || a ? "dni" : "dnevi";
                case"M":
                    return t || a ? "en mesec" : "enim mesecem";
                case"MM":
                    return i += 1 === e ? t || a ? "mesec" : "mesecem" : 2 === e ? t || a ? "meseca" : "mesecema" : e < 5 ? t || a ? "mesece" : "meseci" : t || a ? "mesecev" : "meseci";
                case"y":
                    return t || a ? "eno leto" : "enim letom";
                case"yy":
                    return i += 1 === e ? t || a ? "leto" : "letom" : 2 === e ? t || a ? "leti" : "letoma" : e < 5 ? t || a ? "leta" : "leti" : t || a ? "let" : "leti"
            }
        }

        var n = e.defineLocale("sl", {
            months: "januar_februar_marec_april_maj_junij_julij_avgust_september_oktober_november_december".split("_"),
            monthsShort: "jan._feb._mar._apr._maj._jun._jul._avg._sep._okt._nov._dec.".split("_"),
            monthsParseExact: !0,
            weekdays: "nedelja_ponedeljek_torek_sreda_četrtek_petek_sobota".split("_"),
            weekdaysShort: "ned._pon._tor._sre._čet._pet._sob.".split("_"),
            weekdaysMin: "ne_po_to_sr_če_pe_so".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd, D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[danes ob] LT", nextDay: "[jutri ob] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[v] [nedeljo] [ob] LT";
                        case 3:
                            return "[v] [sredo] [ob] LT";
                        case 6:
                            return "[v] [soboto] [ob] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[v] dddd [ob] LT"
                    }
                }, lastDay: "[včeraj ob] LT", lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[prejšnjo] [nedeljo] [ob] LT";
                        case 3:
                            return "[prejšnjo] [sredo] [ob] LT";
                        case 6:
                            return "[prejšnjo] [soboto] [ob] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[prejšnji] dddd [ob] LT"
                    }
                }, sameElse: "L"
            },
            relativeTime: {
                future: "čez %s",
                past: "pred %s",
                s: t,
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: t,
                dd: t,
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("sq", {
            months: "Janar_Shkurt_Mars_Prill_Maj_Qershor_Korrik_Gusht_Shtator_Tetor_Nëntor_Dhjetor".split("_"),
            monthsShort: "Jan_Shk_Mar_Pri_Maj_Qer_Kor_Gus_Sht_Tet_Nën_Dhj".split("_"),
            weekdays: "E Diel_E Hënë_E Martë_E Mërkurë_E Enjte_E Premte_E Shtunë".split("_"),
            weekdaysShort: "Die_Hën_Mar_Mër_Enj_Pre_Sht".split("_"),
            weekdaysMin: "D_H_Ma_Më_E_P_Sh".split("_"),
            weekdaysParseExact: !0,
            meridiemParse: /PD|MD/,
            isPM: function (e) {
                return "M" === e.charAt(0)
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "PD" : "MD"
            },
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Sot në] LT",
                nextDay: "[Nesër në] LT",
                nextWeek: "dddd [në] LT",
                lastDay: "[Dje në] LT",
                lastWeek: "dddd [e kaluar në] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "në %s",
                past: "%s më parë",
                s: "disa sekonda",
                m: "një minutë",
                mm: "%d minuta",
                h: "një orë",
                hh: "%d orë",
                d: "një ditë",
                dd: "%d ditë",
                M: "një muaj",
                MM: "%d muaj",
                y: "një vit",
                yy: "%d vite"
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {
            words: {
                m: ["jedan minut", "jedne minute"],
                mm: ["minut", "minute", "minuta"],
                h: ["jedan sat", "jednog sata"],
                hh: ["sat", "sata", "sati"],
                dd: ["dan", "dana", "dana"],
                MM: ["mesec", "meseca", "meseci"],
                yy: ["godina", "godine", "godina"]
            }, correctGrammaticalCase: function (e, t) {
                return 1 === e ? t[0] : e >= 2 && e <= 4 ? t[1] : t[2]
            }, translate: function (e, n, a) {
                var i = t.words[a];
                return 1 === a.length ? n ? i[0] : i[1] : e + " " + t.correctGrammaticalCase(e, i)
            }
        }, n = e.defineLocale("sr", {
            months: "januar_februar_mart_april_maj_jun_jul_avgust_septembar_oktobar_novembar_decembar".split("_"),
            monthsShort: "jan._feb._mar._apr._maj_jun_jul_avg._sep._okt._nov._dec.".split("_"),
            monthsParseExact: !0,
            weekdays: "nedelja_ponedeljak_utorak_sreda_četvrtak_petak_subota".split("_"),
            weekdaysShort: "ned._pon._uto._sre._čet._pet._sub.".split("_"),
            weekdaysMin: "ne_po_ut_sr_če_pe_su".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd, D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[danas u] LT", nextDay: "[sutra u] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[u] [nedelju] [u] LT";
                        case 3:
                            return "[u] [sredu] [u] LT";
                        case 6:
                            return "[u] [subotu] [u] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[u] dddd [u] LT"
                    }
                }, lastDay: "[juče u] LT", lastWeek: function () {
                    var e = ["[prošle] [nedelje] [u] LT", "[prošlog] [ponedeljka] [u] LT", "[prošlog] [utorka] [u] LT", "[prošle] [srede] [u] LT", "[prošlog] [četvrtka] [u] LT", "[prošlog] [petka] [u] LT", "[prošle] [subote] [u] LT"];
                    return e[this.day()]
                }, sameElse: "L"
            },
            relativeTime: {
                future: "za %s",
                past: "pre %s",
                s: "nekoliko sekundi",
                m: t.translate,
                mm: t.translate,
                h: t.translate,
                hh: t.translate,
                d: "dan",
                dd: t.translate,
                M: "mesec",
                MM: t.translate,
                y: "godinu",
                yy: t.translate
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {
            words: {
                m: ["један минут", "једне минуте"],
                mm: ["минут", "минуте", "минута"],
                h: ["један сат", "једног сата"],
                hh: ["сат", "сата", "сати"],
                dd: ["дан", "дана", "дана"],
                MM: ["месец", "месеца", "месеци"],
                yy: ["година", "године", "година"]
            }, correctGrammaticalCase: function (e, t) {
                return 1 === e ? t[0] : e >= 2 && e <= 4 ? t[1] : t[2]
            }, translate: function (e, n, a) {
                var i = t.words[a];
                return 1 === a.length ? n ? i[0] : i[1] : e + " " + t.correctGrammaticalCase(e, i)
            }
        }, n = e.defineLocale("sr-cyrl", {
            months: "јануар_фебруар_март_април_мај_јун_јул_август_септембар_октобар_новембар_децембар".split("_"),
            monthsShort: "јан._феб._мар._апр._мај_јун_јул_авг._сеп._окт._нов._дец.".split("_"),
            monthsParseExact: !0,
            weekdays: "недеља_понедељак_уторак_среда_четвртак_петак_субота".split("_"),
            weekdaysShort: "нед._пон._уто._сре._чет._пет._суб.".split("_"),
            weekdaysMin: "не_по_ут_ср_че_пе_су".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM YYYY",
                LLL: "D. MMMM YYYY H:mm",
                LLLL: "dddd, D. MMMM YYYY H:mm"
            },
            calendar: {
                sameDay: "[данас у] LT", nextDay: "[сутра у] LT", nextWeek: function () {
                    switch (this.day()) {
                        case 0:
                            return "[у] [недељу] [у] LT";
                        case 3:
                            return "[у] [среду] [у] LT";
                        case 6:
                            return "[у] [суботу] [у] LT";
                        case 1:
                        case 2:
                        case 4:
                        case 5:
                            return "[у] dddd [у] LT"
                    }
                }, lastDay: "[јуче у] LT", lastWeek: function () {
                    var e = ["[прошле] [недеље] [у] LT", "[прошлог] [понедељка] [у] LT", "[прошлог] [уторка] [у] LT", "[прошле] [среде] [у] LT", "[прошлог] [четвртка] [у] LT", "[прошлог] [петка] [у] LT", "[прошле] [суботе] [у] LT"];
                    return e[this.day()]
                }, sameElse: "L"
            },
            relativeTime: {
                future: "за %s",
                past: "пре %s",
                s: "неколико секунди",
                m: t.translate,
                mm: t.translate,
                h: t.translate,
                hh: t.translate,
                d: "дан",
                dd: t.translate,
                M: "месец",
                MM: t.translate,
                y: "годину",
                yy: t.translate
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("ss", {
            months: "Bhimbidvwane_Indlovana_Indlov'lenkhulu_Mabasa_Inkhwekhweti_Inhlaba_Kholwane_Ingci_Inyoni_Imphala_Lweti_Ingongoni".split("_"),
            monthsShort: "Bhi_Ina_Inu_Mab_Ink_Inh_Kho_Igc_Iny_Imp_Lwe_Igo".split("_"),
            weekdays: "Lisontfo_Umsombuluko_Lesibili_Lesitsatfu_Lesine_Lesihlanu_Umgcibelo".split("_"),
            weekdaysShort: "Lis_Umb_Lsb_Les_Lsi_Lsh_Umg".split("_"),
            weekdaysMin: "Li_Us_Lb_Lt_Ls_Lh_Ug".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY h:mm A",
                LLLL: "dddd, D MMMM YYYY h:mm A"
            },
            calendar: {
                sameDay: "[Namuhla nga] LT",
                nextDay: "[Kusasa nga] LT",
                nextWeek: "dddd [nga] LT",
                lastDay: "[Itolo nga] LT",
                lastWeek: "dddd [leliphelile] [nga] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "nga %s",
                past: "wenteka nga %s",
                s: "emizuzwana lomcane",
                m: "umzuzu",
                mm: "%d emizuzu",
                h: "lihora",
                hh: "%d emahora",
                d: "lilanga",
                dd: "%d emalanga",
                M: "inyanga",
                MM: "%d tinyanga",
                y: "umnyaka",
                yy: "%d iminyaka"
            },
            meridiemParse: /ekuseni|emini|entsambama|ebusuku/,
            meridiem: function (e, t, n) {
                return e < 11 ? "ekuseni" : e < 15 ? "emini" : e < 19 ? "entsambama" : "ebusuku"
            },
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "ekuseni" === t ? e : "emini" === t ? e >= 11 ? e : e + 12 : "entsambama" === t || "ebusuku" === t ? 0 === e ? 0 : e + 12 : void 0
            },
            ordinalParse: /\d{1,2}/,
            ordinal: "%d",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("sv", {
            months: "januari_februari_mars_april_maj_juni_juli_augusti_september_oktober_november_december".split("_"),
            monthsShort: "jan_feb_mar_apr_maj_jun_jul_aug_sep_okt_nov_dec".split("_"),
            weekdays: "söndag_måndag_tisdag_onsdag_torsdag_fredag_lördag".split("_"),
            weekdaysShort: "sön_mån_tis_ons_tor_fre_lör".split("_"),
            weekdaysMin: "sö_må_ti_on_to_fr_lö".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "YYYY-MM-DD",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY [kl.] HH:mm",
                LLLL: "dddd D MMMM YYYY [kl.] HH:mm",
                lll: "D MMM YYYY HH:mm",
                llll: "ddd D MMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Idag] LT",
                nextDay: "[Imorgon] LT",
                lastDay: "[Igår] LT",
                nextWeek: "[På] dddd LT",
                lastWeek: "[I] dddd[s] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "om %s",
                past: "för %s sedan",
                s: "några sekunder",
                m: "en minut",
                mm: "%d minuter",
                h: "en timme",
                hh: "%d timmar",
                d: "en dag",
                dd: "%d dagar",
                M: "en månad",
                MM: "%d månader",
                y: "ett år",
                yy: "%d år"
            },
            ordinalParse: /\d{1,2}(e|a)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "e" : 1 === t ? "a" : 2 === t ? "a" : "e";
                return e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("sw", {
            months: "Januari_Februari_Machi_Aprili_Mei_Juni_Julai_Agosti_Septemba_Oktoba_Novemba_Desemba".split("_"),
            monthsShort: "Jan_Feb_Mac_Apr_Mei_Jun_Jul_Ago_Sep_Okt_Nov_Des".split("_"),
            weekdays: "Jumapili_Jumatatu_Jumanne_Jumatano_Alhamisi_Ijumaa_Jumamosi".split("_"),
            weekdaysShort: "Jpl_Jtat_Jnne_Jtan_Alh_Ijm_Jmos".split("_"),
            weekdaysMin: "J2_J3_J4_J5_Al_Ij_J1".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[leo saa] LT",
                nextDay: "[kesho saa] LT",
                nextWeek: "[wiki ijayo] dddd [saat] LT",
                lastDay: "[jana] LT",
                lastWeek: "[wiki iliyopita] dddd [saat] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s baadaye",
                past: "tokea %s",
                s: "hivi punde",
                m: "dakika moja",
                mm: "dakika %d",
                h: "saa limoja",
                hh: "masaa %d",
                d: "siku moja",
                dd: "masiku %d",
                M: "mwezi mmoja",
                MM: "miezi %d",
                y: "mwaka mmoja",
                yy: "miaka %d"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {1: "௧", 2: "௨", 3: "௩", 4: "௪", 5: "௫", 6: "௬", 7: "௭", 8: "௮", 9: "௯", 0: "௦"}, n = {
            "௧": "1",
            "௨": "2",
            "௩": "3",
            "௪": "4",
            "௫": "5",
            "௬": "6",
            "௭": "7",
            "௮": "8",
            "௯": "9",
            "௦": "0"
        }, a = e.defineLocale("ta", {
            months: "ஜனவரி_பிப்ரவரி_மார்ச்_ஏப்ரல்_மே_ஜூன்_ஜூலை_ஆகஸ்ட்_செப்டெம்பர்_அக்டோபர்_நவம்பர்_டிசம்பர்".split("_"),
            monthsShort: "ஜனவரி_பிப்ரவரி_மார்ச்_ஏப்ரல்_மே_ஜூன்_ஜூலை_ஆகஸ்ட்_செப்டெம்பர்_அக்டோபர்_நவம்பர்_டிசம்பர்".split("_"),
            weekdays: "ஞாயிற்றுக்கிழமை_திங்கட்கிழமை_செவ்வாய்கிழமை_புதன்கிழமை_வியாழக்கிழமை_வெள்ளிக்கிழமை_சனிக்கிழமை".split("_"),
            weekdaysShort: "ஞாயிறு_திங்கள்_செவ்வாய்_புதன்_வியாழன்_வெள்ளி_சனி".split("_"),
            weekdaysMin: "ஞா_தி_செ_பு_வி_வெ_ச".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, HH:mm",
                LLLL: "dddd, D MMMM YYYY, HH:mm"
            },
            calendar: {
                sameDay: "[இன்று] LT",
                nextDay: "[நாளை] LT",
                nextWeek: "dddd, LT",
                lastDay: "[நேற்று] LT",
                lastWeek: "[கடந்த வாரம்] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s இல்",
                past: "%s முன்",
                s: "ஒரு சில விநாடிகள்",
                m: "ஒரு நிமிடம்",
                mm: "%d நிமிடங்கள்",
                h: "ஒரு மணி நேரம்",
                hh: "%d மணி நேரம்",
                d: "ஒரு நாள்",
                dd: "%d நாட்கள்",
                M: "ஒரு மாதம்",
                MM: "%d மாதங்கள்",
                y: "ஒரு வருடம்",
                yy: "%d ஆண்டுகள்"
            },
            ordinalParse: /\d{1,2}வது/,
            ordinal: function (e) {
                return e + "வது"
            },
            preparse: function (e) {
                return e.replace(/[௧௨௩௪௫௬௭௮௯௦]/g, function (e) {
                    return n[e]
                })
            },
            postformat: function (e) {
                return e.replace(/\d/g, function (e) {
                    return t[e]
                })
            },
            meridiemParse: /யாமம்|வைகறை|காலை|நண்பகல்|எற்பாடு|மாலை/,
            meridiem: function (e, t, n) {
                return e < 2 ? " யாமம்" : e < 6 ? " வைகறை" : e < 10 ? " காலை" : e < 14 ? " நண்பகல்" : e < 18 ? " எற்பாடு" : e < 22 ? " மாலை" : " யாமம்"
            },
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "யாமம்" === t ? e < 2 ? e : e + 12 : "வைகறை" === t || "காலை" === t ? e : "நண்பகல்" === t && e >= 10 ? e : e + 12
            },
            week: {dow: 0, doy: 6}
        });
        return a
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("te", {
            months: "జనవరి_ఫిబ్రవరి_మార్చి_ఏప్రిల్_మే_జూన్_జూలై_ఆగస్టు_సెప్టెంబర్_అక్టోబర్_నవంబర్_డిసెంబర్".split("_"),
            monthsShort: "జన._ఫిబ్ర._మార్చి_ఏప్రి._మే_జూన్_జూలై_ఆగ._సెప్._అక్టో._నవ._డిసె.".split("_"),
            monthsParseExact: !0,
            weekdays: "ఆదివారం_సోమవారం_మంగళవారం_బుధవారం_గురువారం_శుక్రవారం_శనివారం".split("_"),
            weekdaysShort: "ఆది_సోమ_మంగళ_బుధ_గురు_శుక్ర_శని".split("_"),
            weekdaysMin: "ఆ_సో_మం_బు_గు_శు_శ".split("_"),
            longDateFormat: {
                LT: "A h:mm",
                LTS: "A h:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY, A h:mm",
                LLLL: "dddd, D MMMM YYYY, A h:mm"
            },
            calendar: {
                sameDay: "[నేడు] LT",
                nextDay: "[రేపు] LT",
                nextWeek: "dddd, LT",
                lastDay: "[నిన్న] LT",
                lastWeek: "[గత] dddd, LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s లో",
                past: "%s క్రితం",
                s: "కొన్ని క్షణాలు",
                m: "ఒక నిమిషం",
                mm: "%d నిమిషాలు",
                h: "ఒక గంట",
                hh: "%d గంటలు",
                d: "ఒక రోజు",
                dd: "%d రోజులు",
                M: "ఒక నెల",
                MM: "%d నెలలు",
                y: "ఒక సంవత్సరం",
                yy: "%d సంవత్సరాలు"
            },
            ordinalParse: /\d{1,2}వ/,
            ordinal: "%dవ",
            meridiemParse: /రాత్రి|ఉదయం|మధ్యాహ్నం|సాయంత్రం/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "రాత్రి" === t ? e < 4 ? e : e + 12 : "ఉదయం" === t ? e : "మధ్యాహ్నం" === t ? e >= 10 ? e : e + 12 : "సాయంత్రం" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "రాత్రి" : e < 10 ? "ఉదయం" : e < 17 ? "మధ్యాహ్నం" : e < 20 ? "సాయంత్రం" : "రాత్రి"
            },
            week: {dow: 0, doy: 6}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("tet", {
            months: "Janeiru_Fevereiru_Marsu_Abril_Maiu_Juniu_Juliu_Augustu_Setembru_Outubru_Novembru_Dezembru".split("_"),
            monthsShort: "Jan_Fev_Mar_Abr_Mai_Jun_Jul_Aug_Set_Out_Nov_Dez".split("_"),
            weekdays: "Domingu_Segunda_Tersa_Kuarta_Kinta_Sexta_Sabadu".split("_"),
            weekdaysShort: "Dom_Seg_Ters_Kua_Kint_Sext_Sab".split("_"),
            weekdaysMin: "Do_Seg_Te_Ku_Ki_Sex_Sa".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Ohin iha] LT",
                nextDay: "[Aban iha] LT",
                nextWeek: "dddd [iha] LT",
                lastDay: "[Horiseik iha] LT",
                lastWeek: "dddd [semana kotuk] [iha] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "iha %s",
                past: "%s liuba",
                s: "minutu balun",
                m: "minutu ida",
                mm: "minutus %d",
                h: "horas ida",
                hh: "horas %d",
                d: "loron ida",
                dd: "loron %d",
                M: "fulan ida",
                MM: "fulan %d",
                y: "tinan ida",
                yy: "tinan %d"
            },
            ordinalParse: /\d{1,2}(st|nd|rd|th)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                return e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("th", {
            months: "มกราคม_กุมภาพันธ์_มีนาคม_เมษายน_พฤษภาคม_มิถุนายน_กรกฎาคม_สิงหาคม_กันยายน_ตุลาคม_พฤศจิกายน_ธันวาคม".split("_"),
            monthsShort: "ม.ค._ก.พ._มี.ค._เม.ย._พ.ค._มิ.ย._ก.ค._ส.ค._ก.ย._ต.ค._พ.ย._ธ.ค.".split("_"),
            monthsParseExact: !0,
            weekdays: "อาทิตย์_จันทร์_อังคาร_พุธ_พฤหัสบดี_ศุกร์_เสาร์".split("_"),
            weekdaysShort: "อาทิตย์_จันทร์_อังคาร_พุธ_พฤหัส_ศุกร์_เสาร์".split("_"),
            weekdaysMin: "อา._จ._อ._พ._พฤ._ศ._ส.".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "H:mm",
                LTS: "H:mm:ss",
                L: "YYYY/MM/DD",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY เวลา H:mm",
                LLLL: "วันddddที่ D MMMM YYYY เวลา H:mm"
            },
            meridiemParse: /ก่อนเที่ยง|หลังเที่ยง/,
            isPM: function (e) {
                return "หลังเที่ยง" === e
            },
            meridiem: function (e, t, n) {
                return e < 12 ? "ก่อนเที่ยง" : "หลังเที่ยง"
            },
            calendar: {
                sameDay: "[วันนี้ เวลา] LT",
                nextDay: "[พรุ่งนี้ เวลา] LT",
                nextWeek: "dddd[หน้า เวลา] LT",
                lastDay: "[เมื่อวานนี้ เวลา] LT",
                lastWeek: "[วัน]dddd[ที่แล้ว เวลา] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "อีก %s",
                past: "%sที่แล้ว",
                s: "ไม่กี่วินาที",
                m: "1 นาที",
                mm: "%d นาที",
                h: "1 ชั่วโมง",
                hh: "%d ชั่วโมง",
                d: "1 วัน",
                dd: "%d วัน",
                M: "1 เดือน",
                MM: "%d เดือน",
                y: "1 ปี",
                yy: "%d ปี"
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("tl-ph", {
            months: "Enero_Pebrero_Marso_Abril_Mayo_Hunyo_Hulyo_Agosto_Setyembre_Oktubre_Nobyembre_Disyembre".split("_"),
            monthsShort: "Ene_Peb_Mar_Abr_May_Hun_Hul_Ago_Set_Okt_Nob_Dis".split("_"),
            weekdays: "Linggo_Lunes_Martes_Miyerkules_Huwebes_Biyernes_Sabado".split("_"),
            weekdaysShort: "Lin_Lun_Mar_Miy_Huw_Biy_Sab".split("_"),
            weekdaysMin: "Li_Lu_Ma_Mi_Hu_Bi_Sab".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "MM/D/YYYY",
                LL: "MMMM D, YYYY",
                LLL: "MMMM D, YYYY HH:mm",
                LLLL: "dddd, MMMM DD, YYYY HH:mm"
            },
            calendar: {
                sameDay: "LT [ngayong araw]",
                nextDay: "[Bukas ng] LT",
                nextWeek: "LT [sa susunod na] dddd",
                lastDay: "LT [kahapon]",
                lastWeek: "LT [noong nakaraang] dddd",
                sameElse: "L"
            },
            relativeTime: {
                future: "sa loob ng %s",
                past: "%s ang nakalipas",
                s: "ilang segundo",
                m: "isang minuto",
                mm: "%d minuto",
                h: "isang oras",
                hh: "%d oras",
                d: "isang araw",
                dd: "%d araw",
                M: "isang buwan",
                MM: "%d buwan",
                y: "isang taon",
                yy: "%d taon"
            },
            ordinalParse: /\d{1,2}/,
            ordinal: function (e) {
                return e
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e) {
            var t = e;
            return t = e.indexOf("jaj") !== -1 ? t.slice(0, -3) + "leS" : e.indexOf("jar") !== -1 ? t.slice(0, -3) + "waQ" : e.indexOf("DIS") !== -1 ? t.slice(0, -3) + "nem" : t + " pIq"
        }

        function n(e) {
            var t = e;
            return t = e.indexOf("jaj") !== -1 ? t.slice(0, -3) + "Hu’" : e.indexOf("jar") !== -1 ? t.slice(0, -3) + "wen" : e.indexOf("DIS") !== -1 ? t.slice(0, -3) + "ben" : t + " ret"
        }

        function a(e, t, n, a) {
            var o = i(e);
            switch (n) {
                case"mm":
                    return o + " tup";
                case"hh":
                    return o + " rep";
                case"dd":
                    return o + " jaj";
                case"MM":
                    return o + " jar";
                case"yy":
                    return o + " DIS"
            }
        }

        function i(e) {
            var t = Math.floor(e % 1e3 / 100), n = Math.floor(e % 100 / 10), a = e % 10, i = "";
            return t > 0 && (i += o[t] + "vatlh"), n > 0 && (i += ("" !== i ? " " : "") + o[n] + "maH"), a > 0 && (i += ("" !== i ? " " : "") + o[a]), "" === i ? "pagh" : i
        }

        var o = "pagh_wa’_cha’_wej_loS_vagh_jav_Soch_chorgh_Hut".split("_"), r = e.defineLocale("tlh", {
            months: "tera’ jar wa’_tera’ jar cha’_tera’ jar wej_tera’ jar loS_tera’ jar vagh_tera’ jar jav_tera’ jar Soch_tera’ jar chorgh_tera’ jar Hut_tera’ jar wa’maH_tera’ jar wa’maH wa’_tera’ jar wa’maH cha’".split("_"),
            monthsShort: "jar wa’_jar cha’_jar wej_jar loS_jar vagh_jar jav_jar Soch_jar chorgh_jar Hut_jar wa’maH_jar wa’maH wa’_jar wa’maH cha’".split("_"),
            monthsParseExact: !0,
            weekdays: "lojmItjaj_DaSjaj_povjaj_ghItlhjaj_loghjaj_buqjaj_ghInjaj".split("_"),
            weekdaysShort: "lojmItjaj_DaSjaj_povjaj_ghItlhjaj_loghjaj_buqjaj_ghInjaj".split("_"),
            weekdaysMin: "lojmItjaj_DaSjaj_povjaj_ghItlhjaj_loghjaj_buqjaj_ghInjaj".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[DaHjaj] LT",
                nextDay: "[wa’leS] LT",
                nextWeek: "LLL",
                lastDay: "[wa’Hu’] LT",
                lastWeek: "LLL",
                sameElse: "L"
            },
            relativeTime: {
                future: t,
                past: n,
                s: "puS lup",
                m: "wa’ tup",
                mm: a,
                h: "wa’ rep",
                hh: a,
                d: "wa’ jaj",
                dd: a,
                M: "wa’ jar",
                MM: a,
                y: "wa’ DIS",
                yy: a
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return r
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = {
            1: "'inci",
            5: "'inci",
            8: "'inci",
            70: "'inci",
            80: "'inci",
            2: "'nci",
            7: "'nci",
            20: "'nci",
            50: "'nci",
            3: "'üncü",
            4: "'üncü",
            100: "'üncü",
            6: "'ncı",
            9: "'uncu",
            10: "'uncu",
            30: "'uncu",
            60: "'ıncı",
            90: "'ıncı"
        }, n = e.defineLocale("tr", {
            months: "Ocak_Şubat_Mart_Nisan_Mayıs_Haziran_Temmuz_Ağustos_Eylül_Ekim_Kasım_Aralık".split("_"),
            monthsShort: "Oca_Şub_Mar_Nis_May_Haz_Tem_Ağu_Eyl_Eki_Kas_Ara".split("_"),
            weekdays: "Pazar_Pazartesi_Salı_Çarşamba_Perşembe_Cuma_Cumartesi".split("_"),
            weekdaysShort: "Paz_Pts_Sal_Çar_Per_Cum_Cts".split("_"),
            weekdaysMin: "Pz_Pt_Sa_Ça_Pe_Cu_Ct".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[bugün saat] LT",
                nextDay: "[yarın saat] LT",
                nextWeek: "[haftaya] dddd [saat] LT",
                lastDay: "[dün] LT",
                lastWeek: "[geçen hafta] dddd [saat] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s sonra",
                past: "%s önce",
                s: "birkaç saniye",
                m: "bir dakika",
                mm: "%d dakika",
                h: "bir saat",
                hh: "%d saat",
                d: "bir gün",
                dd: "%d gün",
                M: "bir ay",
                MM: "%d ay",
                y: "bir yıl",
                yy: "%d yıl"
            },
            ordinalParse: /\d{1,2}'(inci|nci|üncü|ncı|uncu|ıncı)/,
            ordinal: function (e) {
                if (0 === e)return e + "'ıncı";
                var n = e % 10, a = e % 100 - n, i = e >= 100 ? 100 : null;
                return e + (t[n] || t[a] || t[i])
            },
            week: {dow: 1, doy: 7}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t, n, a) {
            var i = {
                s: ["viensas secunds", "'iensas secunds"],
                m: ["'n míut", "'iens míut"],
                mm: [e + " míuts", "" + e + " míuts"],
                h: ["'n þora", "'iensa þora"],
                hh: [e + " þoras", "" + e + " þoras"],
                d: ["'n ziua", "'iensa ziua"],
                dd: [e + " ziuas", "" + e + " ziuas"],
                M: ["'n mes", "'iens mes"],
                MM: [e + " mesen", "" + e + " mesen"],
                y: ["'n ar", "'iens ar"],
                yy: [e + " ars", "" + e + " ars"]
            };
            return a ? i[n][0] : t ? i[n][0] : i[n][1]
        }

        var n = e.defineLocale("tzl", {
            months: "Januar_Fevraglh_Març_Avrïu_Mai_Gün_Julia_Guscht_Setemvar_Listopäts_Noemvar_Zecemvar".split("_"),
            monthsShort: "Jan_Fev_Mar_Avr_Mai_Gün_Jul_Gus_Set_Lis_Noe_Zec".split("_"),
            weekdays: "Súladi_Lúneçi_Maitzi_Márcuri_Xhúadi_Viénerçi_Sáturi".split("_"),
            weekdaysShort: "Súl_Lún_Mai_Már_Xhú_Vié_Sát".split("_"),
            weekdaysMin: "Sú_Lú_Ma_Má_Xh_Vi_Sá".split("_"),
            longDateFormat: {
                LT: "HH.mm",
                LTS: "HH.mm.ss",
                L: "DD.MM.YYYY",
                LL: "D. MMMM [dallas] YYYY",
                LLL: "D. MMMM [dallas] YYYY HH.mm",
                LLLL: "dddd, [li] D. MMMM [dallas] YYYY HH.mm"
            },
            meridiemParse: /d\'o|d\'a/i,
            isPM: function (e) {
                return "d'o" === e.toLowerCase()
            },
            meridiem: function (e, t, n) {
                return e > 11 ? n ? "d'o" : "D'O" : n ? "d'a" : "D'A"
            },
            calendar: {
                sameDay: "[oxhi à] LT",
                nextDay: "[demà à] LT",
                nextWeek: "dddd [à] LT",
                lastDay: "[ieiri à] LT",
                lastWeek: "[sür el] dddd [lasteu à] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "osprei %s",
                past: "ja%s",
                s: t,
                m: t,
                mm: t,
                h: t,
                hh: t,
                d: t,
                dd: t,
                M: t,
                MM: t,
                y: t,
                yy: t
            },
            ordinalParse: /\d{1,2}\./,
            ordinal: "%d.",
            week: {dow: 1, doy: 4}
        });
        return n
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("tzm", {
            months: "ⵉⵏⵏⴰⵢⵔ_ⴱⵕⴰⵢⵕ_ⵎⴰⵕⵚ_ⵉⴱⵔⵉⵔ_ⵎⴰⵢⵢⵓ_ⵢⵓⵏⵢⵓ_ⵢⵓⵍⵢⵓⵣ_ⵖⵓⵛⵜ_ⵛⵓⵜⴰⵏⴱⵉⵔ_ⴽⵟⵓⴱⵕ_ⵏⵓⵡⴰⵏⴱⵉⵔ_ⴷⵓⵊⵏⴱⵉⵔ".split("_"),
            monthsShort: "ⵉⵏⵏⴰⵢⵔ_ⴱⵕⴰⵢⵕ_ⵎⴰⵕⵚ_ⵉⴱⵔⵉⵔ_ⵎⴰⵢⵢⵓ_ⵢⵓⵏⵢⵓ_ⵢⵓⵍⵢⵓⵣ_ⵖⵓⵛⵜ_ⵛⵓⵜⴰⵏⴱⵉⵔ_ⴽⵟⵓⴱⵕ_ⵏⵓⵡⴰⵏⴱⵉⵔ_ⴷⵓⵊⵏⴱⵉⵔ".split("_"),
            weekdays: "ⴰⵙⴰⵎⴰⵙ_ⴰⵢⵏⴰⵙ_ⴰⵙⵉⵏⴰⵙ_ⴰⴽⵔⴰⵙ_ⴰⴽⵡⴰⵙ_ⴰⵙⵉⵎⵡⴰⵙ_ⴰⵙⵉⴹⵢⴰⵙ".split("_"),
            weekdaysShort: "ⴰⵙⴰⵎⴰⵙ_ⴰⵢⵏⴰⵙ_ⴰⵙⵉⵏⴰⵙ_ⴰⴽⵔⴰⵙ_ⴰⴽⵡⴰⵙ_ⴰⵙⵉⵎⵡⴰⵙ_ⴰⵙⵉⴹⵢⴰⵙ".split("_"),
            weekdaysMin: "ⴰⵙⴰⵎⴰⵙ_ⴰⵢⵏⴰⵙ_ⴰⵙⵉⵏⴰⵙ_ⴰⴽⵔⴰⵙ_ⴰⴽⵡⴰⵙ_ⴰⵙⵉⵎⵡⴰⵙ_ⴰⵙⵉⴹⵢⴰⵙ".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[ⴰⵙⴷⵅ ⴴ] LT",
                nextDay: "[ⴰⵙⴽⴰ ⴴ] LT",
                nextWeek: "dddd [ⴴ] LT",
                lastDay: "[ⴰⵚⴰⵏⵜ ⴴ] LT",
                lastWeek: "dddd [ⴴ] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "ⴷⴰⴷⵅ ⵙ ⵢⴰⵏ %s",
                past: "ⵢⴰⵏ %s",
                s: "ⵉⵎⵉⴽ",
                m: "ⵎⵉⵏⵓⴺ",
                mm: "%d ⵎⵉⵏⵓⴺ",
                h: "ⵙⴰⵄⴰ",
                hh: "%d ⵜⴰⵙⵙⴰⵄⵉⵏ",
                d: "ⴰⵙⵙ",
                dd: "%d oⵙⵙⴰⵏ",
                M: "ⴰⵢoⵓⵔ",
                MM: "%d ⵉⵢⵢⵉⵔⵏ",
                y: "ⴰⵙⴳⴰⵙ",
                yy: "%d ⵉⵙⴳⴰⵙⵏ"
            },
            week: {dow: 6, doy: 12}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("tzm-latn", {
            months: "innayr_brˤayrˤ_marˤsˤ_ibrir_mayyw_ywnyw_ywlywz_ɣwšt_šwtanbir_ktˤwbrˤ_nwwanbir_dwjnbir".split("_"),
            monthsShort: "innayr_brˤayrˤ_marˤsˤ_ibrir_mayyw_ywnyw_ywlywz_ɣwšt_šwtanbir_ktˤwbrˤ_nwwanbir_dwjnbir".split("_"),
            weekdays: "asamas_aynas_asinas_akras_akwas_asimwas_asiḍyas".split("_"),
            weekdaysShort: "asamas_aynas_asinas_akras_akwas_asimwas_asiḍyas".split("_"),
            weekdaysMin: "asamas_aynas_asinas_akras_akwas_asimwas_asiḍyas".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[asdkh g] LT",
                nextDay: "[aska g] LT",
                nextWeek: "dddd [g] LT",
                lastDay: "[assant g] LT",
                lastWeek: "dddd [g] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "dadkh s yan %s",
                past: "yan %s",
                s: "imik",
                m: "minuḍ",
                mm: "%d minuḍ",
                h: "saɛa",
                hh: "%d tassaɛin",
                d: "ass",
                dd: "%d ossan",
                M: "ayowr",
                MM: "%d iyyirn",
                y: "asgas",
                yy: "%d isgasn"
            },
            week: {dow: 6, doy: 12}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        function t(e, t) {
            var n = e.split("_");
            return t % 10 === 1 && t % 100 !== 11 ? n[0] : t % 10 >= 2 && t % 10 <= 4 && (t % 100 < 10 || t % 100 >= 20) ? n[1] : n[2]
        }

        function n(e, n, a) {
            var i = {
                mm: n ? "хвилина_хвилини_хвилин" : "хвилину_хвилини_хвилин",
                hh: n ? "година_години_годин" : "годину_години_годин",
                dd: "день_дні_днів",
                MM: "місяць_місяці_місяців",
                yy: "рік_роки_років"
            };
            return "m" === a ? n ? "хвилина" : "хвилину" : "h" === a ? n ? "година" : "годину" : e + " " + t(i[a], +e)
        }

        function a(e, t) {
            var n = {
                nominative: "неділя_понеділок_вівторок_середа_четвер_п’ятниця_субота".split("_"),
                accusative: "неділю_понеділок_вівторок_середу_четвер_п’ятницю_суботу".split("_"),
                genitive: "неділі_понеділка_вівторка_середи_четверга_п’ятниці_суботи".split("_")
            }, a = /(\[[ВвУу]\]) ?dddd/.test(t) ? "accusative" : /\[?(?:минулої|наступної)? ?\] ?dddd/.test(t) ? "genitive" : "nominative";
            return n[a][e.day()]
        }

        function i(e) {
            return function () {
                return e + "о" + (11 === this.hours() ? "б" : "") + "] LT"
            }
        }

        var o = e.defineLocale("uk", {
            months: {
                format: "січня_лютого_березня_квітня_травня_червня_липня_серпня_вересня_жовтня_листопада_грудня".split("_"),
                standalone: "січень_лютий_березень_квітень_травень_червень_липень_серпень_вересень_жовтень_листопад_грудень".split("_")
            },
            monthsShort: "січ_лют_бер_квіт_трав_черв_лип_серп_вер_жовт_лист_груд".split("_"),
            weekdays: a,
            weekdaysShort: "нд_пн_вт_ср_чт_пт_сб".split("_"),
            weekdaysMin: "нд_пн_вт_ср_чт_пт_сб".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD.MM.YYYY",
                LL: "D MMMM YYYY р.",
                LLL: "D MMMM YYYY р., HH:mm",
                LLLL: "dddd, D MMMM YYYY р., HH:mm"
            },
            calendar: {
                sameDay: i("[Сьогодні "),
                nextDay: i("[Завтра "),
                lastDay: i("[Вчора "),
                nextWeek: i("[У] dddd ["),
                lastWeek: function () {
                    switch (this.day()) {
                        case 0:
                        case 3:
                        case 5:
                        case 6:
                            return i("[Минулої] dddd [").call(this);
                        case 1:
                        case 2:
                        case 4:
                            return i("[Минулого] dddd [").call(this)
                    }
                },
                sameElse: "L"
            },
            relativeTime: {
                future: "за %s",
                past: "%s тому",
                s: "декілька секунд",
                m: n,
                mm: n,
                h: "годину",
                hh: n,
                d: "день",
                dd: n,
                M: "місяць",
                MM: n,
                y: "рік",
                yy: n
            },
            meridiemParse: /ночі|ранку|дня|вечора/,
            isPM: function (e) {
                return /^(дня|вечора)$/.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 4 ? "ночі" : e < 12 ? "ранку" : e < 17 ? "дня" : "вечора"
            },
            ordinalParse: /\d{1,2}-(й|го)/,
            ordinal: function (e, t) {
                switch (t) {
                    case"M":
                    case"d":
                    case"DDD":
                    case"w":
                    case"W":
                        return e + "-й";
                    case"D":
                        return e + "-го";
                    default:
                        return e
                }
            },
            week: {dow: 1, doy: 7}
        });
        return o
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("uz", {
            months: "январ_феврал_март_апрел_май_июн_июл_август_сентябр_октябр_ноябр_декабр".split("_"),
            monthsShort: "янв_фев_мар_апр_май_июн_июл_авг_сен_окт_ноя_дек".split("_"),
            weekdays: "Якшанба_Душанба_Сешанба_Чоршанба_Пайшанба_Жума_Шанба".split("_"),
            weekdaysShort: "Якш_Душ_Сеш_Чор_Пай_Жум_Шан".split("_"),
            weekdaysMin: "Як_Ду_Се_Чо_Па_Жу_Ша".split("_"),
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "D MMMM YYYY, dddd HH:mm"
            },
            calendar: {
                sameDay: "[Бугун соат] LT [да]",
                nextDay: "[Эртага] LT [да]",
                nextWeek: "dddd [куни соат] LT [да]",
                lastDay: "[Кеча соат] LT [да]",
                lastWeek: "[Утган] dddd [куни соат] LT [да]",
                sameElse: "L"
            },
            relativeTime: {
                future: "Якин %s ичида",
                past: "Бир неча %s олдин",
                s: "фурсат",
                m: "бир дакика",
                mm: "%d дакика",
                h: "бир соат",
                hh: "%d соат",
                d: "бир кун",
                dd: "%d кун",
                M: "бир ой",
                MM: "%d ой",
                y: "бир йил",
                yy: "%d йил"
            },
            week: {dow: 1, doy: 7}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("vi", {
            months: "tháng 1_tháng 2_tháng 3_tháng 4_tháng 5_tháng 6_tháng 7_tháng 8_tháng 9_tháng 10_tháng 11_tháng 12".split("_"),
            monthsShort: "Th01_Th02_Th03_Th04_Th05_Th06_Th07_Th08_Th09_Th10_Th11_Th12".split("_"),
            monthsParseExact: !0,
            weekdays: "chủ nhật_thứ hai_thứ ba_thứ tư_thứ năm_thứ sáu_thứ bảy".split("_"),
            weekdaysShort: "CN_T2_T3_T4_T5_T6_T7".split("_"),
            weekdaysMin: "CN_T2_T3_T4_T5_T6_T7".split("_"),
            weekdaysParseExact: !0,
            meridiemParse: /sa|ch/i,
            isPM: function (e) {
                return /^ch$/i.test(e)
            },
            meridiem: function (e, t, n) {
                return e < 12 ? n ? "sa" : "SA" : n ? "ch" : "CH"
            },
            longDateFormat: {
                LT: "HH:mm",
                LTS: "HH:mm:ss",
                L: "DD/MM/YYYY",
                LL: "D MMMM [năm] YYYY",
                LLL: "D MMMM [năm] YYYY HH:mm",
                LLLL: "dddd, D MMMM [năm] YYYY HH:mm",
                l: "DD/M/YYYY",
                ll: "D MMM YYYY",
                lll: "D MMM YYYY HH:mm",
                llll: "ddd, D MMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[Hôm nay lúc] LT",
                nextDay: "[Ngày mai lúc] LT",
                nextWeek: "dddd [tuần tới lúc] LT",
                lastDay: "[Hôm qua lúc] LT",
                lastWeek: "dddd [tuần rồi lúc] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "%s tới",
                past: "%s trước",
                s: "vài giây",
                m: "một phút",
                mm: "%d phút",
                h: "một giờ",
                hh: "%d giờ",
                d: "một ngày",
                dd: "%d ngày",
                M: "một tháng",
                MM: "%d tháng",
                y: "một năm",
                yy: "%d năm"
            },
            ordinalParse: /\d{1,2}/,
            ordinal: function (e) {
                return e
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("x-pseudo", {
            months: "J~áñúá~rý_F~ébrú~árý_~Márc~h_Áp~ríl_~Máý_~Júñé~_Júl~ý_Áú~gúst~_Sép~témb~ér_Ó~ctób~ér_Ñ~óvém~bér_~Décé~mbér".split("_"),
            monthsShort: "J~áñ_~Féb_~Már_~Ápr_~Máý_~Júñ_~Júl_~Áúg_~Sép_~Óct_~Ñóv_~Déc".split("_"),
            monthsParseExact: !0,
            weekdays: "S~úñdá~ý_Mó~ñdáý~_Túé~sdáý~_Wéd~ñésd~áý_T~húrs~dáý_~Fríd~áý_S~átúr~dáý".split("_"),
            weekdaysShort: "S~úñ_~Móñ_~Túé_~Wéd_~Thú_~Frí_~Sát".split("_"),
            weekdaysMin: "S~ú_Mó~_Tú_~Wé_T~h_Fr~_Sá".split("_"),
            weekdaysParseExact: !0,
            longDateFormat: {
                LT: "HH:mm",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY HH:mm",
                LLLL: "dddd, D MMMM YYYY HH:mm"
            },
            calendar: {
                sameDay: "[T~ódá~ý át] LT",
                nextDay: "[T~ómó~rró~w át] LT",
                nextWeek: "dddd [át] LT",
                lastDay: "[Ý~ést~érdá~ý át] LT",
                lastWeek: "[L~ást] dddd [át] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "í~ñ %s",
                past: "%s á~gó",
                s: "á ~féw ~sécó~ñds",
                m: "á ~míñ~úté",
                mm: "%d m~íñú~tés",
                h: "á~ñ hó~úr",
                hh: "%d h~óúrs",
                d: "á ~dáý",
                dd: "%d d~áýs",
                M: "á ~móñ~th",
                MM: "%d m~óñt~hs",
                y: "á ~ýéár",
                yy: "%d ý~éárs"
            },
            ordinalParse: /\d{1,2}(th|st|nd|rd)/,
            ordinal: function (e) {
                var t = e % 10, n = 1 === ~~(e % 100 / 10) ? "th" : 1 === t ? "st" : 2 === t ? "nd" : 3 === t ? "rd" : "th";
                return e + n
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("yo", {
            months: "Sẹ́rẹ́_Èrèlè_Ẹrẹ̀nà_Ìgbé_Èbibi_Òkùdu_Agẹmo_Ògún_Owewe_Ọ̀wàrà_Bélú_Ọ̀pẹ̀̀".split("_"),
            monthsShort: "Sẹ́r_Èrl_Ẹrn_Ìgb_Èbi_Òkù_Agẹ_Ògú_Owe_Ọ̀wà_Bél_Ọ̀pẹ̀̀".split("_"),
            weekdays: "Àìkú_Ajé_Ìsẹ́gun_Ọjọ́rú_Ọjọ́bọ_Ẹtì_Àbámẹ́ta".split("_"),
            weekdaysShort: "Àìk_Ajé_Ìsẹ́_Ọjr_Ọjb_Ẹtì_Àbá".split("_"),
            weekdaysMin: "Àì_Aj_Ìs_Ọr_Ọb_Ẹt_Àb".split("_"),
            longDateFormat: {
                LT: "h:mm A",
                LTS: "h:mm:ss A",
                L: "DD/MM/YYYY",
                LL: "D MMMM YYYY",
                LLL: "D MMMM YYYY h:mm A",
                LLLL: "dddd, D MMMM YYYY h:mm A"
            },
            calendar: {
                sameDay: "[Ònì ni] LT",
                nextDay: "[Ọ̀la ni] LT",
                nextWeek: "dddd [Ọsẹ̀ tón'bọ] [ni] LT",
                lastDay: "[Àna ni] LT",
                lastWeek: "dddd [Ọsẹ̀ tólọ́] [ni] LT",
                sameElse: "L"
            },
            relativeTime: {
                future: "ní %s",
                past: "%s kọjá",
                s: "ìsẹjú aayá die",
                m: "ìsẹjú kan",
                mm: "ìsẹjú %d",
                h: "wákati kan",
                hh: "wákati %d",
                d: "ọjọ́ kan",
                dd: "ọjọ́ %d",
                M: "osù kan",
                MM: "osù %d",
                y: "ọdún kan",
                yy: "ọdún %d"
            },
            ordinalParse: /ọjọ́\s\d{1,2}/,
            ordinal: "ọjọ́ %d",
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("zh-cn", {
            months: "一月_二月_三月_四月_五月_六月_七月_八月_九月_十月_十一月_十二月".split("_"),
            monthsShort: "1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月".split("_"),
            weekdays: "星期日_星期一_星期二_星期三_星期四_星期五_星期六".split("_"),
            weekdaysShort: "周日_周一_周二_周三_周四_周五_周六".split("_"),
            weekdaysMin: "日_一_二_三_四_五_六".split("_"),
            longDateFormat: {
                LT: "Ah点mm分",
                LTS: "Ah点m分s秒",
                L: "YYYY-MM-DD",
                LL: "YYYY年MMMD日",
                LLL: "YYYY年MMMD日Ah点mm分",
                LLLL: "YYYY年MMMD日ddddAh点mm分",
                l: "YYYY-MM-DD",
                ll: "YYYY年MMMD日",
                lll: "YYYY年MMMD日Ah点mm分",
                llll: "YYYY年MMMD日ddddAh点mm分"
            },
            meridiemParse: /凌晨|早上|上午|中午|下午|晚上/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "凌晨" === t || "早上" === t || "上午" === t ? e : "下午" === t || "晚上" === t ? e + 12 : e >= 11 ? e : e + 12
            },
            meridiem: function (e, t, n) {
                var a = 100 * e + t;
                return a < 600 ? "凌晨" : a < 900 ? "早上" : a < 1130 ? "上午" : a < 1230 ? "中午" : a < 1800 ? "下午" : "晚上"
            },
            calendar: {
                sameDay: function () {
                    return 0 === this.minutes() ? "[今天]Ah[点整]" : "[今天]LT"
                }, nextDay: function () {
                    return 0 === this.minutes() ? "[明天]Ah[点整]" : "[明天]LT"
                }, lastDay: function () {
                    return 0 === this.minutes() ? "[昨天]Ah[点整]" : "[昨天]LT"
                }, nextWeek: function () {
                    var t, n;
                    return t = e().startOf("week"), n = this.diff(t, "days") >= 7 ? "[下]" : "[本]", 0 === this.minutes() ? n + "dddAh点整" : n + "dddAh点mm"
                }, lastWeek: function () {
                    var t, n;
                    return t = e().startOf("week"), n = this.unix() < t.unix() ? "[上]" : "[本]", 0 === this.minutes() ? n + "dddAh点整" : n + "dddAh点mm"
                }, sameElse: "LL"
            },
            ordinalParse: /\d{1,2}(日|月|周)/,
            ordinal: function (e, t) {
                switch (t) {
                    case"d":
                    case"D":
                    case"DDD":
                        return e + "日";
                    case"M":
                        return e + "月";
                    case"w":
                    case"W":
                        return e + "周";
                    default:
                        return e
                }
            },
            relativeTime: {
                future: "%s内",
                past: "%s前",
                s: "几秒",
                m: "1 分钟",
                mm: "%d 分钟",
                h: "1 小时",
                hh: "%d 小时",
                d: "1 天",
                dd: "%d 天",
                M: "1 个月",
                MM: "%d 个月",
                y: "1 年",
                yy: "%d 年"
            },
            week: {dow: 1, doy: 4}
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("zh-hk", {
            months: "一月_二月_三月_四月_五月_六月_七月_八月_九月_十月_十一月_十二月".split("_"),
            monthsShort: "1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月".split("_"),
            weekdays: "星期日_星期一_星期二_星期三_星期四_星期五_星期六".split("_"),
            weekdaysShort: "週日_週一_週二_週三_週四_週五_週六".split("_"),
            weekdaysMin: "日_一_二_三_四_五_六".split("_"),
            longDateFormat: {
                LT: "Ah點mm分",
                LTS: "Ah點m分s秒",
                L: "YYYY年MMMD日",
                LL: "YYYY年MMMD日",
                LLL: "YYYY年MMMD日Ah點mm分",
                LLLL: "YYYY年MMMD日ddddAh點mm分",
                l: "YYYY年MMMD日",
                ll: "YYYY年MMMD日",
                lll: "YYYY年MMMD日Ah點mm分",
                llll: "YYYY年MMMD日ddddAh點mm分"
            },
            meridiemParse: /凌晨|早上|上午|中午|下午|晚上/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "凌晨" === t || "早上" === t || "上午" === t ? e : "中午" === t ? e >= 11 ? e : e + 12 : "下午" === t || "晚上" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                var a = 100 * e + t;
                return a < 600 ? "凌晨" : a < 900 ? "早上" : a < 1130 ? "上午" : a < 1230 ? "中午" : a < 1800 ? "下午" : "晚上"
            },
            calendar: {
                sameDay: "[今天]LT",
                nextDay: "[明天]LT",
                nextWeek: "[下]ddddLT",
                lastDay: "[昨天]LT",
                lastWeek: "[上]ddddLT",
                sameElse: "L"
            },
            ordinalParse: /\d{1,2}(日|月|週)/,
            ordinal: function (e, t) {
                switch (t) {
                    case"d":
                    case"D":
                    case"DDD":
                        return e + "日";
                    case"M":
                        return e + "月";
                    case"w":
                    case"W":
                        return e + "週";
                    default:
                        return e
                }
            },
            relativeTime: {
                future: "%s內",
                past: "%s前",
                s: "幾秒",
                m: "1 分鐘",
                mm: "%d 分鐘",
                h: "1 小時",
                hh: "%d 小時",
                d: "1 天",
                dd: "%d 天",
                M: "1 個月",
                MM: "%d 個月",
                y: "1 年",
                yy: "%d 年"
            }
        });
        return t
    })
}, function (e, t, n) {
    !function (e, t) {
        t(n(51))
    }(this, function (e) {
        "use strict";
        var t = e.defineLocale("zh-tw", {
            months: "一月_二月_三月_四月_五月_六月_七月_八月_九月_十月_十一月_十二月".split("_"),
            monthsShort: "1月_2月_3月_4月_5月_6月_7月_8月_9月_10月_11月_12月".split("_"),
            weekdays: "星期日_星期一_星期二_星期三_星期四_星期五_星期六".split("_"),
            weekdaysShort: "週日_週一_週二_週三_週四_週五_週六".split("_"),
            weekdaysMin: "日_一_二_三_四_五_六".split("_"),
            longDateFormat: {
                LT: "Ah點mm分",
                LTS: "Ah點m分s秒",
                L: "YYYY年MMMD日",
                LL: "YYYY年MMMD日",
                LLL: "YYYY年MMMD日Ah點mm分",
                LLLL: "YYYY年MMMD日ddddAh點mm分",
                l: "YYYY年MMMD日",
                ll: "YYYY年MMMD日",
                lll: "YYYY年MMMD日Ah點mm分",
                llll: "YYYY年MMMD日ddddAh點mm分"
            },
            meridiemParse: /凌晨|早上|上午|中午|下午|晚上/,
            meridiemHour: function (e, t) {
                return 12 === e && (e = 0), "凌晨" === t || "早上" === t || "上午" === t ? e : "中午" === t ? e >= 11 ? e : e + 12 : "下午" === t || "晚上" === t ? e + 12 : void 0
            },
            meridiem: function (e, t, n) {
                var a = 100 * e + t;
                return a < 600 ? "凌晨" : a < 900 ? "早上" : a < 1130 ? "上午" : a < 1230 ? "中午" : a < 1800 ? "下午" : "晚上"
            },
            calendar: {
                sameDay: "[今天]LT",
                nextDay: "[明天]LT",
                nextWeek: "[下]ddddLT",
                lastDay: "[昨天]LT",
                lastWeek: "[上]ddddLT",
                sameElse: "L"
            },
            ordinalParse: /\d{1,2}(日|月|週)/,
            ordinal: function (e, t) {
                switch (t) {
                    case"d":
                    case"D":
                    case"DDD":
                        return e + "日";
                    case"M":
                        return e + "月";
                    case"w":
                    case"W":
                        return e + "週";
                    default:
                        return e
                }
            },
            relativeTime: {
                future: "%s內",
                past: "%s前",
                s: "幾秒",
                m: "1 分鐘",
                mm: "%d 分鐘",
                h: "1 小時",
                hh: "%d 小時",
                d: "1 天",
                dd: "%d 天",
                M: "1 個月",
                MM: "%d 個月",
                y: "1 年",
                yy: "%d 年"
            }
        });
        return t
    })
}, function (e, t, n) {
    (function (t) {
        function a(e, t) {
            var n = o.enc.Utf8.parse(t || key), a = null;
            try {
                a = o.DES.decrypt({ciphertext: o.enc.Base64.parse(e)}, n, {mode: o.mode.ECB, padding: o.pad.Pkcs7})
            } catch (i) {
                return
            }
            return JSON.parse(a.toString(o.enc.Utf8))
        }

        const i = n(16), o = n(17), r = t("#control_login"), s = t("#control_vip");
        n(162);
        var l = !1, d = !1, c = {};
        e.exports = {
            init: function (e) {
                const t = i.get("p");
                if (d = !1, !t)return e(!1);
                const n = a(t, "ymark-login-user-info");
                return n && n.id && (n.completeInfo || n.phone || n.name) ? (this.setUser(n), d = !0, r.addClass("over-status"), c = n, e({
                    username: n.username,
                    avatar: n.avatar,
                    end: n.vip_end_time || "",
                    ci: n.completeInfo,
                    id: n.id
                })) : e(!1)
            }, getUser: function () {
                return c
            }, setUser: function (e) {
                if (e) {
                    l = 1 == e.is_vip, d = !0, l ? t("#span_fan_hongbao").text("5") : t("#span_fan_hongbao").text("3"), t("#input-list").removeAttr("readonly"), t("#jieba-input-list").removeAttr("readonly"), t("#jieba-easy-input-list").removeAttr("readonly");
                    const n = t("#config-background");
                    if (r.addClass("over-status"), t("[setyq]").text(e.yq), t("#yqPanel").removeClass("hide"), 1 == e.is_vip) {
                        var a = "", i = parseInt((new Date(e.vip_end_time).getTime() - (new Date).getTime()) / 1e3);
                        i <= 0 ? (a = "成为VIP", l = !1) : a = i <= 3600 ? "VIP仅剩余<br/>" + parseInt(i / 60) + "分钟" : i <= 86400 ? "VIP仅剩余<br/>" + parseInt(i / 3600) + "个小时" : i <= 2678400 ? "VIP还剩余<br/>" + parseInt(i / 86400) + "天" : i <= 32140800 ? "VIP还剩余<br/>" + parseInt(i / 2678400) + "个月" : "VIP剩余<br/>" + parseInt(i / 31536e3) + "年"
                    }
                    t("#sub_nav_trigger_invite_list").removeClass("hide"), t("#invite_info_every_fee").text(1 == e.is_vip ? "5" : "3"), e.ewm_url && "-" != e.ewm_url && t("#modal_hb_panel").addClass("modal_hd_over"), s.removeClass("hide").html(a), n.colpick({
                        color: "#333",
                        submit: 0,
                        onChange: function (e, t) {
                            n.val("#" + t).css("border-color", "#" + t)
                        }
                    })
                }
            }, getPayUrl: function () {
                return !(!c || !c.ewm_url || "-" == c.ewm_url) && c.ewm_url
            }, loginOut: function () {
                i.remove("p", {path: "", domain: ".yciyun.com"}), window.location.reload()
            }, isVip: function () {
                return !!d && l
            }, isLogin: function () {
                return d
            }
        }
    }).call(t, n(1))
}, function (e, t, n) {
    (function (t) {
        function a(e, t) {
            var n = t - e + 1;
            return Math.floor(Math.random() * n + e)
        }

        const i = n(162), o = n(2), r = n(9), s = n(164);
        var l = t("#input-list"), d = t("#jieba_btn_splitword"), c = (t("#jieba_btn_statistic"), t("#btn_random_color")), u = t("#select_theme"), m = t("#jieba_btn_expect_word"), _ = t("#jieba-input-list"), p = [], h = {}, f = !0, g = !1, y = [2, 5], v = !0, b = !1;
        window.rand = a;
        var w = o.get("simple-toggle") || !1;
        window.toggleToSimple = function () {
            o.set("simple-toggle", !0, 172800), w = !0
        };
        var M=Mlist;
        //var M = [{name: "先生", size: 9, num: 1, color: "#2d8cf0"}];
       /* var M = [{name: "先生", size: 9, num: 1, color: "#2d8cf0"}, {
            name: "因为",
            size: 7,
            num: 1,
            color: "#2d8cf0"
        }, {name: "一个", size: 7, num: 1, color: "#2d8cf0"}, {
            name: "时候",
            size: 7,
            num: 1,
            color: "#B8FFB8"
        }, {name: "而且", size: 6, num: 1, color: "#008000"}, {
            name: "可以",
            size: 6,
            num: 1,
            color: "#2d8cf0"
        }, {name: "起来", size: 6, num: 1, color: "#2d8cf0"}, {
            name: "遇见",
            size: 6,
            num: 1,
            color: "#2d8cf0"
        }, {name: "美女", size: 6, num: 1, color: "#2d8cf0"}, {
            name: "和尚",
            size: 6,
            num: 1,
            color: "#2d8cf0"
        }, {name: "后来", size: 6, num: 1, color: "#2d8cf0"}, {
            name: "现在",
            size: 6,
            num: 1,
            color: "#2d8cf0"
        }, {name: "读书", size: 6, num: 1, color: "#2d8cf0"}, {
            name: "不知道",
            size: 6,
            num: 1,
            color: "#B8FFB8"
        }, {name: "百草", size: 6, num: 1, color: "#2d8cf0"}, {
            name: "声音",
            size: 6,
            num: 1,
            color: "#2d8cf0"
        }, {name: "这是", size: 6, num: 1, color: "#2d8cf0"}, {
            name: "下来",
            size: 6,
            num: 1,
            color: "#2d8cf0"
        }, {name: "知道", size: 5, num: 1, color: "#2d8cf0"}, {
            name: "父亲",
            size: 5,
            num: 1,
            color: "#008000"
        }, {name: "他是", size: 5, num: 1, color: "#2d8cf0"}, {
            name: "没有",
            size: 5,
            num: 1,
            color: "#008000"
        }, {name: "蜈蚣", size: 5, num: 1, color: "#2d8cf0"}, {
            name: "我们",
            size: 5,
            num: 1,
            color: "#2d8cf0"
        }, {name: "自己", size: 5, num: 1, color: "#2d8cf0"}, {
            name: "答应",
            size: 5,
            num: 1,
            color: "#008000"
        }, {name: "一条", size: 5, num: 1, color: "#008000"}, {
            name: "这里",
            size: 5,
            num: 1,
            color: "#2d8cf0"
        }, {name: "后面", size: 5, num: 1, color: "#B8FFB8"}, {
            name: "故事",
            size: 5,
            num: 1,
            color: "#B8FFB8"
        }, {name: "木莲", size: 5, num: 1, color: "#2d8cf0"}, {
            name: "何首乌",
            size: 5,
            num: 1,
            color: "#B8FFB8"
        }, {name: "中间", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "算是",
            size: 4,
            num: 1,
            color: "#008000"
        }, {name: "行礼", size: 4, num: 1, color: "#008000"}, {
            name: "睡不着",
            size: 4,
            num: 1,
            color: "#B8FFB8"
        }, {name: "蟋蟀", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "进去",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "闰土", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "半天",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "不行", size: 4, num: 1, color: "#008000"}, {
            name: "怪哉",
            size: 4,
            num: 1,
            color: "#008000"
        }, {name: "鸟雀", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "得到",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "地面", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "有了",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "习字", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "书塾",
            size: 4,
            num: 1,
            color: "#008000"
        }, {name: "回去", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "高兴",
            size: 4,
            num: 1,
            color: "#008000"
        }, {name: "多起来", size: 4, num: 1, color: "#008000"}, {
            name: "桑椹",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "也不", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "听到",
            size: 4,
            num: 1,
            color: "#B8FFB8"
        }, {name: "第二次", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "只要",
            size: 4,
            num: 1,
            color: "#B8FFB8"
        }, {name: "早已", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "书房",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "名字", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "书屋",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "已经", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "虽然",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "似乎", size: 4, num: 1, color: "#008000"}, {
            name: "所以",
            size: 4,
            num: 1,
            color: "#B8FFB8"
        }, {name: "枕边", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "有人",
            size: 4,
            num: 1,
            color: "#008000"
        }, {name: "金光", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "还有",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "常常", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "然而",
            size: 4,
            num: 1,
            color: "#B8FFB8"
        }, {name: "盒子", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "卖给",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "自然", size: 4, num: 1, color: "#008000"}, {
            name: "叫作",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "曾经", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "相传",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "总是", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "常用",
            size: 4,
            num: 1,
            color: "#B8FFB8"
        }, {name: "往往", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "渊博",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "哪里", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "大的",
            size: 4,
            num: 1,
            color: "#008000"
        }, {name: "也许", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "很大的",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "什么", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "一种",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "有些", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "一块",
            size: 4,
            num: 1,
            color: "#B8FFB8"
        }, {name: "石井栏", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "一道",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "伏在", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "不过",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "覆盆子", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "一阵",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "孔子", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "脸上",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "赤练蛇", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "同窗",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "下面", size: 4, num: 1, color: "#2d8cf0"}, {
            name: "绣像",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "竹筛", size: 4, num: 1, color: "#B8FFB8"}, {
            name: "Ade",
            size: 4,
            num: 1,
            color: "#2d8cf0"
        }, {name: "规则", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "九月十八日",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "草丛", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "教训",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "脑髓", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "过去",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "忽然", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "有时",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "风雨", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "不断",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "人名", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "最初",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "无妨", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "晚上",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "照样", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "正午",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "怪物", size: 3, num: 1, color: "#008000"}, {
            name: "冬天",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "当然", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "夏夜",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "果然", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "不是",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "机关", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "半夜",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "不敢", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "夜间",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "比较", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "晚间",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "久已", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "先前",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "无处", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "七八年",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "远远", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "雪人",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "明明", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "听说",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "墙头", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "要钱",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "无从", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "不少",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "毕竟", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "全形",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "将要", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "画画",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "赶忙", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "做戏",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "决不", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "用纸",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "至于", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "微笑",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "渐渐", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "颠倒",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "渐渐地", size: 3, num: 1, color: "#008000"}, {
            name: "指挥",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "终于", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "朗读",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "最好", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "下去",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "陆续", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "念书",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "真是", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "鼎沸",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "快要", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "放开",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "朱文", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "罗汉",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "院子", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "大叫",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "读书人", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "加上",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "间壁", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "积雪",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "梁家", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "乃是",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "妈妈", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "所谓",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "加多", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "秕谷",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "文章", size: 3, num: 1, color: "#008000"}, {
            name: "应该",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "荆川", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "上了",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "最末", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "一回事",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "周围", size: 3, num: 1, color: "#008000"}, {
            name: "消释",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "外面", size: 3, num: 1, color: "#008000"}, {
            name: "名曰",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "旁边", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "认识",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "小球", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "听来",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "底下", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "不知",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "珊瑚", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "答礼",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "上半", size: 3, num: 1, color: "#008000"}, {
            name: "长绳",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "其中", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "绳子",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "那时", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "桂花",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "人样", size: 3, num: 1, color: "#008000"}, {
            name: "便是",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "块根", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "走过",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "人形", size: 3, num: 1, color: "#008000"}, {
            name: "出门",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "那样", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "不能",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "这样", size: 3, num: 1, color: "#008000"}, {
            name: "所得",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "果实", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "许是",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "人们", size: 3, num: 1, color: "#008000"}, {
            name: "称为",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "它们", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "麻雀",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "为什么", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "来不及",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "莲房", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "捕获",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "烟雾", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "捉住",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "怎么", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "飞鸟",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "这些", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "传授",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "大家", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "过夜",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "有一些", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "居多",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "四面", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "啄食",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "三四", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "性子",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "几十", size: 3, num: 1, color: "#008000"}, {
            name: "露出",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "三间", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "觅食",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "第一次", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "方法",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "脊梁", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "鉴赏",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "好几", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "需要",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "一个一个", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "不大",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "斑蝥", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "得失",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "鸣蝉", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "直到",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "草间", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "乘凉",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "油蛉", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "做人",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "断砖", size: 3, num: 1, color: "#008000"}, {
            name: "觉得",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "用手", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "不可",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "藤缠络", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "回来",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "成仙", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "缘由",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "弄坏", size: 3, num: 1, color: "#008000"}, {
            name: "像是",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "泥墙", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "要死",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "从来没有", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "识破",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "珠攒", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "夜谈",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "色味", size: 3, num: 1, color: "#008000"}, {
            name: "走来",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "这园", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "全城",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "脸露", size: 3, num: 1, color: "#008000"}, {
            name: "砖头",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "趣味", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "石桥",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "便要", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "纳凉",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "这人", size: 3, num: 1, color: "#008000"}, {
            name: "住在",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "墙根", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "牵连",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "门外", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "梅花鹿",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "死了", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "牌位",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "很使", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "老人",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "还没有", size: 3, num: 1, color: "#008000"}, {
            name: "乌有",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "塑雪", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "喷出",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "荒园", size: 3, num: 1, color: "#008000"}, {
            name: "按住",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "两天", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "须发",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "云霄", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "眼镜",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "撒些", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "翻开",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "便罩住", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "弹琴",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "白颊", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "无限",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "很躁", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "长吟",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "云雀", size: 3, num: 1, color: "#008000"}, {
            name: "相见",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "天子", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "东方朔",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "叉袋", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "冤气",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "笑道", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "机会",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "人要", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "有钱的",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "送进", size: 3, num: 1, color: "#008000"}, {
            name: "相宜的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "毁了", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "入神的",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "泥墙罢", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "极好的",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "我将", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "淋漓",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "向东", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "倜傥",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "竹门", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "如意",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "匾道", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "大声",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "幅画", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "低下",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "黄蜂", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "普通",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "戴着", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "怒色",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "树叶", size: 3, num: 1, color: "#008000"}, {
            name: "严厉",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "用酒", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "渊博的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "很想", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "详细地",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "这虫", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "学生",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "生书", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "博学的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "很不", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "质朴",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "不愿意", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "方正",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "几天", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "恭敬",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "在那里", size: 3, num: 1, color: "#008000"}, {
            name: "和蔼地",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "爬上", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "严厉的",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "太多", size: 3, num: 1, color: "#008000"}, {
            name: "性急",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "不行了", size: 3, num: 1, color: "#008000"}, {
            name: "能用",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "罚跪", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "宿儒",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "仁远", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "相宜",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "欲仁斯仁至矣", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "无味",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "笑人齿缺", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "有的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "狗窦", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "陌生",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "潜龙勿用", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "和美",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "厥土", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "担心",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "错厥贡苞茅橘柚", size: 3, num: 1, color: "#008000"}, {
            name: "陌生的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "金叵罗", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "妖气",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "醉嗬", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "突然",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "读到", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "用功",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "几个", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "长的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "我是", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "要好得",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "影写", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "臃肿的",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "荡寇志", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "一般的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "短短", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "轻捷的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "薄薄", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "肥胖的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "静静", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "高大的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "花白", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "光滑的",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "静悄悄", size: 3, num: 1, color: "#008000"}, {
            name: "确凿",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "一带", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "年纪",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "倘若", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "上一个个",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "于是", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "花坛",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "因此", size: 3, num: 1, color: "#008000"}, {
            name: "一扇",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "如果", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "一面",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "紫红", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "一支",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "皂荚", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "腊梅",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "菜畦", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "两样",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "碧绿", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "一下",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "乐园", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "一盒",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "野草", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "蝉蜕",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "子孙", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "一团",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "还是", size: 3, num: 1, color: "#008000"}, {
            name: "工作",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "总而言之", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "苍蝇",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "如此", size: 3, num: 1, color: "#008000"}, {
            name: "一起",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "但是", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "蚂蚁",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "只有", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "东西",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "庙里", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "地位",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "屋子", size: 3, num: 1, color: "#008000"}, {
            name: "绅士",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "西游记", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "店主",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "墙上", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "锡箔",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "雪上", size: 3, num: 1, color: "#008000"}, {
            name: "大本",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "家里", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "片段",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "地上", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "成绩",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "树上", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "戒尺",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "一定", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "小说",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "万万", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "指甲",
            size: 3,
            num: 1,
            color: "#B8FFB8"
        }, {name: "一同", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "盔甲",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "高枕而卧", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "疑心",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "沙沙沙", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "人声",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "想得到", size: 3, num: 1, color: "#B8FFB8"}, {
            name: "喉咙",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "人迹罕至", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "声道",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "中极", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "不必",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "一旁", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "黑油",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "七言", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "飞出",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "肥大", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "上上",
            size: 3,
            num: 1,
            color: "#2d8cf0"
        }, {name: "一坐皆惊", size: 3, num: 1, color: "#2d8cf0"}, {
            name: "我家",
            size: 3,
            num: 1,
            color: "#008000"
        }, {name: "一样", size: 3, num: 1, color: "#2d8cf0"}, {name: "菜花", size: 3, num: 1, color: "#008000"}
        ];*/
        const k = o.get("ywordle-cache-data");
        k && (M = k);
        const L = t("#table-handle-panel"), x = t("#tableHtml"), Y = "#2d8cf0", D = function (e, t) {
            const n = [];
            return n.push("<tr>"), n.push('    <td idx="' + t + '" col="size"><div class="text">' + e.size + "</div></td>"), n.push('    <td idx="' + t + '" col="name"><div class="text">' + e.name + "</div></td>"), n.push('    <td idx="' + t + '" col="color"><div style="color:' + (e.color || Y) + ';" class="text text-color">' + (e.color || Y) + "</div></td>"), n.push('    <td idx="' + t + '" col="num"><div class="text">' + e.num + "</div></td>"), n.push('    <td idx="' + t + '" col="display"><div class="text">' + (e.display || "是") + "</div></td>"), n.push("</tr>"), n.join("")
        }, T = function () {
            x.html('<div class="table-load">loading...</div>');
            const e = [];
            for (var t = 0, n = M.length; t < n; t++)e.push(D(M[t], t));
            x.html(e.join(""))
        }, S = function (e) {
            h = {};
            const t = {}, n = [];
            for (var r, s, l = 0, d = M.length; l < d; l++)if (r = M[l], "否" != r.display && (s = [r.name, r.size], r.color && (t[r.name] = r.color), n.push(s), r.num && r.num > 1))for (var c, u = 1; u < r.num; u++)c = f ? [r.name, a(y[0], y[1])] : s, n.push(c);
            h = t, n.length < 120 && console.log("小于三百数量不够，需要早自动插入一些数据:", n.length), p = n, e || (g = !1, z(), i.isVip() && (o.set("ywordle-cache-data", M), w ? window.Noti.notif({
                msg: "<div style='line-height:30px;'>已保存</div>",
                type: "success",
                position: "right",
                width: 100,
                height: 30,
                timeout: 2e3
            }) : window.Noti.notif({
                msg: "<div style='line-height:22px;margin-top:10px;'>为防止刷新页面后数据消失，已将数据保存到本地浏览器中<br/>缓存时间为1个小时<span style='margin-left:10px;cursor:pointer;color:#f4633b;text-decoration:underline;' onclick='window.toggleToSimple()'>点击改为简单提示</span></div>",
                type: "warning",
                position: "center",
                timeout: 3e4
            })))
        }, z = function () {
            const e = [];
            for (var t, n = 0, a = M.length; n < a; n++)t = M[n], e.push(t.size + "|" + t.name + "|" + (t.color || Y) + "|" + t.num + "|" + (t.display || "是"));
            l.val(e.join("\n"))
        };
        var H = [["#d4006a", "#d9d9d3", "#9aa6a6", "#7a7a7a", "#746060"], ["#2d8cf0", "#29609b", "#9aa6a6", "#7a7a7a", "#746060"], ["#2d8cf0", "#008000", "#2d8cf0", "#B8FFB8", "#2d8cf0"], ["#5FD9CD", "#EAF786", "#FFB5A1", "#B8FFB8", "#B8F4FF"], ["#DB9019", "#5ED5D1", "#1A2D27", "#FF6E97", "#F1AAA6"], ["#AAB8A3", "#EBEDF4", "#FF5983", "#C1194E", "#7A023C"]];
        const j = [t("#input_color_item_0"), t("#input_color_item_1"), t("#input_color_item_2"), t("#input_color_item_3"), t("#input_color_item_4")], F = function (e, t) {
            const n = e / t;
            return n >= .9 ? 0 : n >= .6 ? 1 : n >= .4 ? 2 : n >= .25 ? 3 : 4
        }, P = function (e) {
            g = !0;
            const t = [j[0].val() || "", j[1].val() || "", j[2].val() || "", j[3].val() || "", j[4].val() || ""], n = M.length;
            if ("random" == e)for (var i = 0; i < n; i++)M[i].color = t[a(0, 4)]; else {
                for (var o, r = 0, s = 0, i = 0; i < n; i++)o = M[i], o._num ? o._num > r && (r = o._num) : o.size > s && (s = o.size);
                if (r <= 0)for (var i = 0; i < n; i++)M[i].color = t[F(M[i].size, s)]; else for (var i = 0; i < n; i++)M[i].color = t[F(M[i]._num, r)]
            }
            T(), z()
        };
        const C = function () {
            const e = [];
            for (var n = 0, a = H.length; n < a; n++)e.push('<option value="">第' + (n + 1) + "种</option>");
            u.html(e.join(""));
            const o = t("#select_theme_panel");
            u.selectric({
                optionsItemBuilder: function (e, t, n) {
                    const a = H[e.index], i = [];
                    for (var o = 0, r = a.length; o < r; o++)i.push('<span style="background:' + a[o] + '"></span>');
                    return '<div class="color-group-item">' + i.join("") + "</div>"
                }, onChange: function (e, t, n) {
                    if (!i.isLogin())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    if (!i.isVip())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>可开通VIP免费体验尝试修改<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看</span></div>",
                        type: "error",
                        timeout: 1e4
                    });
                    const a = o.find(".selectric-items .selectric-scroll .selected").attr("data-index");
                    if (a) {
                        const r = H[a];
                        j[0].val(r[0] || "").css("color", r[0] || ""), j[1].val(r[1] || "").css("color", r[1] || ""), j[2].val(r[2] || "").css("color", r[2] || ""), j[3].val(r[3] || "").css("color", r[3] || ""), j[4].val(r[4] || "").css("color", r[4] || ""), P("random")
                    }
                }
            });
            const r = H[0];
            j[0].val(r[0] || "").css("color", r[0] || ""), j[1].val(r[1] || "").css("color", r[1] || ""), j[2].val(r[2] || "").css("color", r[2] || ""), j[3].val(r[3] || "").css("color", r[3] || ""), j[4].val(r[4] || "").css("color", r[4] || ""), i.isVip() ? o.find(".input-color-item").removeAttr("readonly") : o.find(".input-color-item").click(function () {
                return i.isLogin() ? window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>可开通VIP免费体验尝试修改<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看</span></div>",
                    type: "error",
                    timeout: 1e4
                }) : window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                    type: "error",
                    timeout: 3e4
                })
            }), j[0].blur(function () {
                j[0].css("color", t(this).val() || "")
            }), j[1].blur(function () {
                j[1].css("color", t(this).val() || "")
            }), j[2].blur(function () {
                j[2].css("color", t(this).val() || "")
            }), j[3].blur(function () {
                j[3].css("color", t(this).val() || "")
            }), j[4].blur(function () {
                j[4].css("color", t(this).val() || "")
            })
        }, E = t("#cx_main_type_list"), A = t("#type_item_list"), B = t("#create_word_list_btn_by_statistic"), I = function (e) {
            var n = [], a = {};
            E.html(""), A.html("");
            var i = 0;
            for (var o in e) {
                const r = e[o];
                var s = 0, l = [], d = 0;
                for (var c in r)s += r[c], d = r[c], i < d && (i = d), l.push({name: c, num: d, checked: !0});
                a[o] = l, n.push('<li><input _main_nm="' + o + '" name="cx_check" type="checkbox" checked> <span nm="' + o + '">' + o + "(" + s + ")</span></li>")
            }
            return E.html(n.join("")), E.find("span").click(function () {
                const e = t(this).parent("li");
                e.siblings("li").removeClass("active"), e.addClass("active");
                const n = t(this).attr("nm");
                var i = a[n];
                i = i.sort(function (e, t) {
                    return t.num - e.num
                });
                const o = [];
                for (var r = 0, s = i.length; r < s; r++)o.push('<li><label><input name="cz_check" type="checkbox" ' + (i[r].checked ? "checked" : "") + ' _type_nm="' + n + '" idx="' + r + '">' + i[r].name + "(" + i[r].num + ")</label></li>");
                A.html(o.join(""))
            }), E.find('[name="cx_check"]').click(function () {
                const e = this.checked, n = t(this).attr("_main_nm");
                for (var i = 0, o = a[n].length; i < o; i++)a[n][i].checked = e;
                A.find('[_type_nm="' + n + '"]').each(function () {
                    this.checked = e
                })
            }), A.on("click", '[name="cz_check"]', function () {
                const e = t(this), n = this.checked, i = e.attr("_type_nm");
                a[i][e.attr("idx")] && (a[i][e.attr("idx")].checked = n), n && (t('[_main_nm="' + i + '"]')[0].checked = !0)
            }), {
                getResultData: function () {
                    return a
                }
            }
        }, W = function (e, t) {
            const n = e / t;
            return 1 == n ? 9 : n >= .8 ? 8 : n >= .7 ? 7 : n >= .5 ? 6 : n >= .3 ? 5 : n >= .2 ? 4 : n >= .1 ? 3 : 2
        }, O = function (e) {
            const t = e.length;
            for (var n = 0, a = 0; a < t; a++)e[a]._num > n && (n = e[a]._num);
            for (var a = 0; a < t; a++)e[a].size = W(e[a]._num, n);
            e = e.sort(function (e, t) {
                return t.size > e.size ? 1 : -1
            });
            for (var i, o, r = 0, a = 0; a < t; a++)0 != a && (i = e[a - 1], o = e[a], i.size - o.size > 1 && (o.size = i.size - 2), o.size >= 3 && o.size <= 5 && r++);
            if (r / t < .5) {
                console.log("中间词量不够！");
                for (var o, a = 0; a < t; a++)e[a].size <= 3 && (e[a].size += 1)
            }
            return e
        };
        e.exports = {
            init: function () {
                T(), S(!0), z(), window.inputOnBlur = function (e) {
                    g = !0;
                    const n = t(e);
                    var a = n.val();
                    a = a.replace(/\s+/gi, ""), a = a.replace(/，/gi, ",");
                    const i = n.parent(".input-text"), o = i.prev(".text"), r = i.parent("td");
                    o.text(a), "color" == r.attr("col") && o.css("color", a), M[r.attr("idx")][r.attr("col")] = a, r.removeClass("active"), z()
                };
                l.change(function () {
                    if (!i.isLogin())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>注册登录后即可更改自定义的数据<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    g = !0;
                    for (var e, t = [], n = l.val().split("\n"), a = 0, o = n.length; a < o; a++)e = n[a].split("|"), 5 == e.length && t.push({
                        name: e[1],
                        size: parseInt(e[0]),
                        num: parseInt(e[3]),
                        color: e[2],
                        display: e[4] || "是"
                    });
                    t.length > 0 && (M = t, T())
                }), _.change(function () {
                    return i.isLogin() ? i.isVip() ? void 0 : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>可开通VIP体验尝试修改<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                        type: "error",
                        timeout: 3e4
                    }) : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    })
                });
                const e = t("#jieba-easy-input-list"), n = t("#dataset_simple_config_word_number");
                t("#jieba_btn_splitword_easy_random").click(function () {
                    if (!i.isLogin())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error", timeout: 3e4
                    });
                    if (!i.isVip())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>邀请好友，使用此功能<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    var o = e.val();
                    if (o) {
                        o = o.replace(/:|：|,|，|\.|。|;|；|\\|、|\s+/gi, "-"), o = o.split("-"), e.val(o.join(","));
                        var r = [];
                        const s = "random" == n.val(), l = H[0];
                        for (var d, c = 0, u = o.length; c < u; c++)d = o[c], d && r.push({
                            name: d,
                            size: a(3, 8),
                            num: s ? a(1, 20) : 1,
                            color: l[a(0, 5)] || "#2d8bf0"
                        });
                        r = r.sort(function (e, t) {
                            return parseInt(t.size) > parseInt(e.size) ? 1 : -1
                        }), g = !0, M = r, T(), z(), t('[directto="dataset_simple"]').click(), setTimeout(function () {
                            t("#btn-reload").click()
                        }, 200)
                    }
                }), c.click(function () {
                    return i.isLogin() ? i.isVip() ? void P("random") : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>可开通VIP体验随机着色功能<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                        type: "error",
                        timeout: 3e4
                    }) : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    })
                }), t("#btn_order_color").click(function () {
                    return i.isLogin() ? i.isVip() ? void P("order") : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>可开通VIP体验顺序着色功能<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                        type: "error",
                        timeout: 3e4
                    }) : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    })
                }), C();
                const o = t("#data_top_panel_color"), u = t("#data_top_panel_statistic");
                t("#left_panel_data .tabs-panel [directTo]").click(function () {
                    const e = t(this).attr("directTo");
                    "dataset_simple" == e || "dataset_complex" == e ? (o.removeClass("hide"), u.addClass("hide")) : "dataset_jiebasplit" == e && (u.removeClass("hide"), o.addClass("hide"))
                }), t("#check_excloud_single_word").click(function () {
                    v = this.checked
                }), t("#check_set_num_1").click(function () {
                    b = this.checked
                });
                const p = t("#expect_word_panel");
                var h = null;
                d.click(function () {
                    const e = _.val();
                    if (!i.isLogin())return window._hmt.push(["_trackEvent", "data", "spliteword", "not-logined"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>需要登录才能够分词<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>去登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    if (e.length > 1e4)return window.Noti.notif({
                        msg: "<div style='line-height:22px;'>当前字数为:" + e.length + "<br/>因为服务器性能不足，暂仅支持字数在1万(19kb左右)以内的文章<br/>如果您有心，可以开通会员或打赏支持，相当于0.2天的维护成本。" + (i.isVip() ? "" : "<br/><div onclick='window.showVIP()' style='color:white;text-decoration:underline;'>去开通</div>") + "<div style='border-top: 1px dashed rgba(208, 208, 208, 0.57); margin-top:5px; padding-top:3px;'>解决方法：您可以减少文章的字数</div></div>",
                        type: "error",
                        multiline: !0,
                        clickable: !1,
                        width: 420,
                        timeout: 3e4
                    });
                    window._hmt.push(["_trackEvent", "data", "spliteword", "logined"]);
                    const t = window.Noti.notif({
                        msg: "<div style='line-height:30px;'>请求中...</div>",
                        type: "info",
                        timeout: 3e5,
                        position: "right",
                        width: 100,
                        height: 30
                    });
                    r.splitWord(e, function (e) {
                        t.dismiss();
                        const n = {}, a = {};
                        for (var i, o, r = 0, l = e.length; r < l; r++)o = e[r].w, '~—‘’“”…《》!（）：；！？?<>+"\\'.indexOf(o) >= 0 || v && o.length <= 1 || (i = s.chsName(e[r].p), n[i] || (n[i] = {}), n[i][o] = (n[i][o] || 0) + 1, a[o] || (a[o] = 0), a[o] = a[o] + 1);
                        h = I(n), p.removeClass("hide")
                    })
                }), t("#reinput_data_btn").click(function () {
                    p.addClass("hide")
                }), B.click(function () {
                    if (!i.isLogin())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    if (!i.isVip())return window._hmt.push(["_trackEvent", "data", "create-by-cx-list", "not-logined"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>可开通VIP免费体验尝试修改<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    window._hmt.push(["_trackEvent", "data", "create-by-cx-list", "logined"]);
                    const e = h.getResultData();
                    var n = [];
                    for (var a in e) {
                        const o = e[a];
                        for (var r, s = 0, d = o.length; s < d; s++)r = o[s], r.checked && n.push({
                            name: r.name,
                            size: 0,
                            num: b ? 1 : r.num,
                            _num: r.num,
                            color: "#2d8cf0"
                        })
                    }
                    M = O(n);
                    const c = [];
                    for (var a, s = 0, d = n.length; s < d; s++)a = n[s], c.push(a.size + "|" + a.name + "|#2d8cf0|" + a.num);
                    t('[directto="dataset_simple"]').click(), g = !0, l.val(c.join("\n")), T(), P("random")
                });
                const f = t("#modal_word_paichu");
                m.click(function () {
                    return window._hmt.push(["_trackEvent", "modal", "vip", "show"]), i.isLogin() ? i.isVip() ? void f.addClass("modal-show") : (window._hmt.push(["_trackEvent", "word", "expect-word", "not-allow"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP会员后即可使用排除单词的功能<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>点我查看</span></div>",
                        type: "error",
                        timeout: 3e4
                    })) : window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    })
                });
                const y = function (e) {
                    if (!i.isLogin())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    if (!i.isVip())return window._hmt.push(["_trackEvent", "data-table", "contrl-display", "not-allow"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>开通会员后，可直接点击修改显示与隐藏<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    const t = e.attr("idx"), n = e.children(".text");
                    var a = !1;
                    g = !0, "是" == n.text().trim() ? (n.text("否"), a = "否") : (n.text("是"), a = "是"), M[t][e.attr("col")] = a, z()
                };
                L.on("click", "td", function () {
                    const e = t(this);
                    if ("color" != e.attr("col")) {
                        if ("display" == e.attr("col"))return y(e);
                        const n = (e.text() || "").replace(/\s+/gi, "");
                        if (e.children(".input-text").length <= 0) {
                            const a = [];
                            a.push('<div class="input-text">'), a.push('\t<input class="input" onblur="window.inputOnBlur(this)" value="' + n + '"/>'), a.push("</div>"), e.append(a.join(""))
                        } else e.find("input").val(n);
                        g = !0, e.addClass("active"), e.find(".input").focus()
                    }
                }), t("#dataset_simple th[data-sort]").click(function () {
                    if (!i.isLogin())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    if (!i.isVip())return window._hmt.push(["_trackEvent", "data", "sort", "not-logined"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>开通VIP后即可任意排序<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    const e = t(this).attr("data-sort");
                    g = !0, x.html('<div class="table-load">loading...</div>'), M = M.sort(function (t, n) {
                        return parseInt(n[e]) > parseInt(t[e]) ? 1 : -1
                    }), T(), z()
                })
            }, setColor: function (e, t) {
                g = !0, M[e].color = t
            }, getData: function () {
                return g && S(), {data: p, color: h}
            }
        }
    }).call(t, n(1))
}, function (e, t) {
    "use strict";
    var n = {};
    n.D_A = 1073741824, n.D_B = 536870912, n.D_C = 268435456, n.D_D = 134217728, n.D_E = 67108864, n.D_F = 33554432, n.D_I = 16777216, n.D_L = 8388608, n.A_M = 4194304, n.D_MQ = 2097152, n.D_N = 1048576, n.D_O = 524288, n.D_P = 262144, n.A_Q = 131072, n.D_R = 65536, n.D_S = 32768, n.D_T = 16384, n.D_U = 8192, n.D_V = 4096, n.D_W = 2048, n.D_X = 1024, n.D_Y = 512, n.D_Z = 256, n.A_NR = 128, n.A_NS = 64, n.A_NT = 32, n.A_NX = 16, n.A_NZ = 8, n.D_ZH = 4, n.D_K = 2, n.UNK = 0, n.URL = 1;
    var a = {};
    for (var i in n)a[i] = n[i];
    for (var i in n)n[i.toLowerCase()] = n[i];
    n.chsName = function (e) {
        if (isNaN(e))return o[e] || o.UNK;
        var t = [];
        for (var n in a)(e & a[n]) > 0 && t.push(o[n]);
        return t.length < 1 ? o.UNK : t.toString()
    };
    var o = n.CHSNAME = {};
    n.CHSNAME.D_A = "形容词", n.CHSNAME.D_B = "区别词", n.CHSNAME.D_C = "连词", n.CHSNAME.D_D = "副词", n.CHSNAME.D_E = "叹词", n.CHSNAME.D_F = "方位词", n.CHSNAME.D_I = "成语", n.CHSNAME.D_L = "习语", n.CHSNAME.A_M = "数词", n.CHSNAME.D_MQ = "数量词", n.CHSNAME.D_N = "名词", n.CHSNAME.D_O = "拟声词", n.CHSNAME.D_P = "介词", n.CHSNAME.A_Q = "量词", n.CHSNAME.D_R = "代词", n.CHSNAME.D_S = "处所词", n.CHSNAME.D_T = "时间词", n.CHSNAME.D_U = "助词", n.CHSNAME.D_V = "动词", n.CHSNAME.D_W = "标点符号", n.CHSNAME.D_X = "非语素字", n.CHSNAME.D_Y = "语气词", n.CHSNAME.D_Z = "状态词", n.CHSNAME.A_NR = "人名", n.CHSNAME.A_NS = "地名", n.CHSNAME.A_NT = "机构团体", n.CHSNAME.A_NX = "外文字符", n.CHSNAME.A_NZ = "其他专名", n.CHSNAME.D_ZH = "前接成分", n.CHSNAME.D_K = "后接成分", n.CHSNAME.UNK = "未知", n.CHSNAME.URL = "网址";
    for (var i in o)o[i.toLowerCase()] = o[i];
    e.exports = n
}, function (e, t, n) {
    var a;
    !function () {
        function i(e, t, n) {
            return e.call.apply(e.bind, arguments)
        }

        function o(e, t, n) {
            if (!e)throw Error();
            if (2 < arguments.length) {
                var a = Array.prototype.slice.call(arguments, 2);
                return function () {
                    var n = Array.prototype.slice.call(arguments);
                    return Array.prototype.unshift.apply(n, a), e.apply(t, n)
                }
            }
            return function () {
                return e.apply(t, arguments)
            }
        }

        function r(e, t, n) {
            return r = Function.prototype.bind && -1 != Function.prototype.bind.toString().indexOf("native code") ? i : o, r.apply(null, arguments)
        }

        function s(e, t) {
            this.a = e, this.o = t || e, this.c = this.o.document
        }

        function l(e, t, n, a) {
            if (t = e.c.createElement(t), n)for (var i in n)n.hasOwnProperty(i) && ("style" == i ? t.style.cssText = n[i] : t.setAttribute(i, n[i]));
            return a && t.appendChild(e.c.createTextNode(a)), t
        }

        function d(e, t, n) {
            e = e.c.getElementsByTagName(t)[0], e || (e = document.documentElement), e.insertBefore(n, e.lastChild)
        }

        function c(e) {
            e.parentNode && e.parentNode.removeChild(e)
        }

        function u(e, t, n) {
            t = t || [], n = n || [];
            for (var a = e.className.split(/\s+/), i = 0; i < t.length; i += 1) {
                for (var o = !1, r = 0; r < a.length; r += 1)if (t[i] === a[r]) {
                    o = !0;
                    break
                }
                o || a.push(t[i])
            }
            for (t = [], i = 0; i < a.length; i += 1) {
                for (o = !1, r = 0; r < n.length; r += 1)if (a[i] === n[r]) {
                    o = !0;
                    break
                }
                o || t.push(a[i])
            }
            e.className = t.join(" ").replace(/\s+/g, " ").replace(/^\s+|\s+$/, "")
        }

        function m(e, t) {
            for (var n = e.className.split(/\s+/), a = 0, i = n.length; a < i; a++)if (n[a] == t)return !0;
            return !1
        }

        function _(e) {
            return e.o.location.hostname || e.a.location.hostname
        }

        function p(e, t, n) {
            function a() {
                s && i && o && (s(r), s = null)
            }

            t = l(e, "link", {rel: "stylesheet", href: t, media: "all"});
            var i = !1, o = !0, r = null, s = n || null;
            oe ? (t.onload = function () {
                i = !0, a()
            }, t.onerror = function () {
                i = !0, r = Error("Stylesheet failed to load"), a()
            }) : setTimeout(function () {
                i = !0, a()
            }, 0), d(e, "head", t)
        }

        function h(e, t, n, a) {
            var i = e.c.getElementsByTagName("head")[0];
            if (i) {
                var o = l(e, "script", {src: t}), r = !1;
                return o.onload = o.onreadystatechange = function () {
                    r || this.readyState && "loaded" != this.readyState && "complete" != this.readyState || (r = !0, n && n(null), o.onload = o.onreadystatechange = null, "HEAD" == o.parentNode.tagName && i.removeChild(o))
                }, i.appendChild(o), setTimeout(function () {
                    r || (r = !0, n && n(Error("Script load timeout")))
                }, a || 5e3), o
            }
            return null
        }

        function f() {
            this.a = 0, this.c = null
        }

        function g(e) {
            return e.a++, function () {
                e.a--, v(e)
            }
        }

        function y(e, t) {
            e.c = t, v(e)
        }

        function v(e) {
            0 == e.a && e.c && (e.c(), e.c = null)
        }

        function b(e) {
            this.a = e || "-"
        }

        function w(e, t) {
            this.c = e, this.f = 4, this.a = "n";
            var n = (t || "n4").match(/^([nio])([1-9])$/i);
            n && (this.a = n[1], this.f = parseInt(n[2], 10))
        }

        function M(e) {
            return x(e) + " " + (e.f + "00") + " 300px " + k(e.c)
        }

        function k(e) {
            var t = [];
            e = e.split(/,\s*/);
            for (var n = 0; n < e.length; n++) {
                var a = e[n].replace(/['"]/g, "");
                -1 != a.indexOf(" ") || /^\d/.test(a) ? t.push("'" + a + "'") : t.push(a)
            }
            return t.join(",")
        }

        function L(e) {
            return e.a + e.f
        }

        function x(e) {
            var t = "normal";
            return "o" === e.a ? t = "oblique" : "i" === e.a && (t = "italic"), t
        }

        function Y(e) {
            var t = 4, n = "n", a = null;
            return e && ((a = e.match(/(normal|oblique|italic)/i)) && a[1] && (n = a[1].substr(0, 1).toLowerCase()), (a = e.match(/([1-9]00|normal|bold)/i)) && a[1] && (/bold/i.test(a[1]) ? t = 7 : /[1-9]00/.test(a[1]) && (t = parseInt(a[1].substr(0, 1), 10)))), n + t
        }

        function D(e, t) {
            this.c = e, this.f = e.o.document.documentElement, this.h = t, this.a = new b("-"), this.j = !1 !== t.events, this.g = !1 !== t.classes
        }

        function T(e) {
            e.g && u(e.f, [e.a.c("wf", "loading")]), z(e, "loading")
        }

        function S(e) {
            if (e.g) {
                var t = m(e.f, e.a.c("wf", "active")), n = [], a = [e.a.c("wf", "loading")];
                t || n.push(e.a.c("wf", "inactive")), u(e.f, n, a)
            }
            z(e, "inactive")
        }

        function z(e, t, n) {
            e.j && e.h[t] && (n ? e.h[t](n.c, L(n)) : e.h[t]())
        }

        function H() {
            this.c = {}
        }

        function j(e, t, n) {
            var a, i = [];
            for (a in t)if (t.hasOwnProperty(a)) {
                var o = e.c[a];
                o && i.push(o(t[a], n))
            }
            return i
        }

        function F(e, t) {
            this.c = e, this.f = t, this.a = l(this.c, "span", {"aria-hidden": "true"}, this.f)
        }

        function P(e) {
            d(e.c, "body", e.a)
        }

        function C(e) {
            return "display:block;position:absolute;top:-9999px;left:-9999px;font-size:300px;width:auto;height:auto;line-height:normal;margin:0;padding:0;font-variant:normal;white-space:nowrap;font-family:" + k(e.c) + ";" + ("font-style:" + x(e) + ";font-weight:" + (e.f + "00") + ";")
        }

        function E(e, t, n, a, i, o) {
            this.g = e, this.j = t, this.a = a, this.c = n, this.f = i || 3e3, this.h = o || void 0
        }

        function A(e, t, n, a, i, o, r) {
            this.v = e, this.B = t, this.c = n, this.a = a, this.s = r || "BESbswy", this.f = {}, this.w = i || 3e3, this.u = o || null, this.m = this.j = this.h = this.g = null, this.g = new F(this.c, this.s), this.h = new F(this.c, this.s), this.j = new F(this.c, this.s), this.m = new F(this.c, this.s), e = new w(this.a.c + ",serif", L(this.a)), e = C(e), this.g.a.style.cssText = e, e = new w(this.a.c + ",sans-serif", L(this.a)), e = C(e), this.h.a.style.cssText = e, e = new w("serif", L(this.a)), e = C(e), this.j.a.style.cssText = e, e = new w("sans-serif", L(this.a)), e = C(e), this.m.a.style.cssText = e, P(this.g), P(this.h), P(this.j), P(this.m)
        }

        function B() {
            if (null === se) {
                var e = /AppleWebKit\/([0-9]+)(?:\.([0-9]+))/.exec(window.navigator.userAgent);
                se = !!e && (536 > parseInt(e[1], 10) || 536 === parseInt(e[1], 10) && 11 >= parseInt(e[2], 10))
            }
            return se
        }

        function I(e, t, n) {
            for (var a in re)if (re.hasOwnProperty(a) && t === e.f[re[a]] && n === e.f[re[a]])return !0;
            return !1
        }

        function W(e) {
            var t, n = e.g.a.offsetWidth, a = e.h.a.offsetWidth;
            (t = n === e.f.serif && a === e.f["sans-serif"]) || (t = B() && I(e, n, a)), t ? ie() - e.A >= e.w ? B() && I(e, n, a) && (null === e.u || e.u.hasOwnProperty(e.a.c)) ? R(e, e.v) : R(e, e.B) : O(e) : R(e, e.v)
        }

        function O(e) {
            setTimeout(r(function () {
                W(this)
            }, e), 50)
        }

        function R(e, t) {
            setTimeout(r(function () {
                c(this.g.a), c(this.h.a), c(this.j.a), c(this.m.a), t(this.a)
            }, e), 0)
        }

        function N(e, t, n) {
            this.c = e, this.a = t, this.f = 0, this.m = this.j = !1, this.s = n
        }

        function V(e) {
            0 == --e.f && e.j && (e.m ? (e = e.a, e.g && u(e.f, [e.a.c("wf", "active")], [e.a.c("wf", "loading"), e.a.c("wf", "inactive")]), z(e, "active")) : S(e.a))
        }

        function J(e) {
            this.j = e, this.a = new H, this.h = 0, this.f = this.g = !0
        }

        function U(e, t, n, a, i) {
            var o = 0 == --e.h;
            (e.f || e.g) && setTimeout(function () {
                var e = i || null, s = a || null || {};
                if (0 === n.length && o)S(t.a); else {
                    t.f += n.length, o && (t.j = o);
                    var l, d = [];
                    for (l = 0; l < n.length; l++) {
                        var c = n[l], m = s[c.c], _ = t.a, p = c;
                        if (_.g && u(_.f, [_.a.c("wf", p.c, L(p).toString(), "loading")]), z(_, "fontloading", p), _ = null, null === le)if (window.FontFace) {
                            var p = /Gecko.*Firefox\/(\d+)/.exec(window.navigator.userAgent), h = /OS X.*Version\/10\..*Safari/.exec(window.navigator.userAgent) && /Apple/.exec(window.navigator.vendor);
                            le = p ? 42 < parseInt(p[1], 10) : !h
                        } else le = !1;
                        _ = le ? new E(r(t.g, t), r(t.h, t), t.c, c, t.s, m) : new A(r(t.g, t), r(t.h, t), t.c, c, t.s, e, m), d.push(_)
                    }
                    for (l = 0; l < d.length; l++)d[l].start()
                }
            }, 0)
        }

        function q(e, t, n) {
            var a = [], i = n.timeout;
            T(t);
            var a = j(e.a, n, e.c), o = new N(e.c, t, i);
            for (e.h = a.length, t = 0, n = a.length; t < n; t++)a[t].load(function (t, n, a) {
                U(e, o, t, n, a)
            })
        }

        function G(e, t) {
            this.c = e, this.a = t
        }

        function $(e, t) {
            this.c = e, this.a = t
        }

        function K(e, t) {
            e ? this.c = e : this.c = de, this.a = [], this.f = [], this.g = t || ""
        }

        function X(e, t) {
            for (var n = t.length, a = 0; a < n; a++) {
                var i = t[a].split(":");
                3 == i.length && e.f.push(i.pop());
                var o = "";
                2 == i.length && "" != i[1] && (o = ":"), e.a.push(i.join(o))
            }
        }

        function Z(e) {
            if (0 == e.a.length)throw Error("No fonts to load!");
            if (-1 != e.c.indexOf("kit="))return e.c;
            for (var t = e.a.length, n = [], a = 0; a < t; a++)n.push(e.a[a].replace(/ /g, "+"));
            return t = e.c + "?family=" + n.join("%7C"), 0 < e.f.length && (t += "&subset=" + e.f.join(",")), 0 < e.g.length && (t += "&text=" + encodeURIComponent(e.g)), t
        }

        function Q(e) {
            this.f = e, this.a = [], this.c = {}
        }

        function ee(e) {
            for (var t = e.f.length, n = 0; n < t; n++) {
                var a = e.f[n].split(":"), i = a[0].replace(/\+/g, " "), o = ["n4"];
                if (2 <= a.length) {
                    var r, s = a[1];
                    if (r = [], s)for (var s = s.split(","), l = s.length, d = 0; d < l; d++) {
                        var c;
                        if (c = s[d], c.match(/^[\w-]+$/)) {
                            var u = _e.exec(c.toLowerCase());
                            if (null == u)c = ""; else {
                                if (c = u[2], c = null == c || "" == c ? "n" : me[c], u = u[1], null == u || "" == u)u = "4"; else var m = ue[u], u = m ? m : isNaN(u) ? "4" : u.substr(0, 1);
                                c = [c, u].join("")
                            }
                        } else c = "";
                        c && r.push(c)
                    }
                    0 < r.length && (o = r), 3 == a.length && (a = a[2], r = [], a = a ? a.split(",") : r, 0 < a.length && (a = ce[a[0]]) && (e.c[i] = a))
                }
                for (e.c[i] || (a = ce[i]) && (e.c[i] = a), a = 0; a < o.length; a += 1)e.a.push(new w(i, o[a]))
            }
        }

        function te(e, t) {
            this.c = e, this.a = t
        }

        function ne(e, t) {
            this.c = e, this.a = t
        }

        function ae(e, t) {
            this.c = e, this.f = t, this.a = []
        }

        var ie = Date.now || function () {
                return +new Date
            }, oe = !!window.FontFace;
        b.prototype.c = function (e) {
            for (var t = [], n = 0; n < arguments.length; n++)t.push(arguments[n].replace(/[\W_]+/g, "").toLowerCase());
            return t.join(this.a)
        }, E.prototype.start = function () {
            var e = this.c.o.document, t = this, n = ie(), a = new Promise(function (a, i) {
                function o() {
                    ie() - n >= t.f ? i() : e.fonts.load(M(t.a), t.h).then(function (e) {
                        1 <= e.length ? a() : setTimeout(o, 25)
                    }, function () {
                        i()
                    })
                }

                o()
            }), i = null, o = new Promise(function (e, n) {
                i = setTimeout(n, t.f)
            });
            Promise.race([o, a]).then(function () {
                i && (clearTimeout(i), i = null), t.g(t.a)
            }, function () {
                t.j(t.a)
            })
        };
        var re = {D: "serif", C: "sans-serif"}, se = null;
        A.prototype.start = function () {
            this.f.serif = this.j.a.offsetWidth, this.f["sans-serif"] = this.m.a.offsetWidth, this.A = ie(), W(this)
        };
        var le = null;
        N.prototype.g = function (e) {
            var t = this.a;
            t.g && u(t.f, [t.a.c("wf", e.c, L(e).toString(), "active")], [t.a.c("wf", e.c, L(e).toString(), "loading"), t.a.c("wf", e.c, L(e).toString(), "inactive")]), z(t, "fontactive", e), this.m = !0, V(this)
        }, N.prototype.h = function (e) {
            var t = this.a;
            if (t.g) {
                var n = m(t.f, t.a.c("wf", e.c, L(e).toString(), "active")), a = [], i = [t.a.c("wf", e.c, L(e).toString(), "loading")];
                n || a.push(t.a.c("wf", e.c, L(e).toString(), "inactive")), u(t.f, a, i)
            }
            z(t, "fontinactive", e), V(this)
        }, J.prototype.load = function (e) {
            this.c = new s(this.j, e.context || this.j), this.g = !1 !== e.events, this.f = !1 !== e.classes, q(this, new D(this.c, e), e)
        }, G.prototype.load = function (e) {
            function t() {
                if (o["__mti_fntLst" + a]) {
                    var n, i = o["__mti_fntLst" + a](), r = [];
                    if (i)for (var s = 0; s < i.length; s++) {
                        var l = i[s].fontfamily;
                        void 0 != i[s].fontStyle && void 0 != i[s].fontWeight ? (n = i[s].fontStyle + i[s].fontWeight, r.push(new w(l, n))) : r.push(new w(l))
                    }
                    e(r)
                } else setTimeout(function () {
                    t()
                }, 50)
            }

            var n = this, a = n.a.projectId, i = n.a.version;
            if (a) {
                var o = n.c.o;
                h(this.c, (n.a.api || "https://fast.fonts.net/jsapi") + "/" + a + ".js" + (i ? "?v=" + i : ""), function (i) {
                    i ? e([]) : (o["__MonotypeConfiguration__" + a] = function () {
                        return n.a
                    }, t())
                }).id = "__MonotypeAPIScript__" + a
            } else e([])
        }, $.prototype.load = function (e) {
            var t, n, a = this.a.urls || [], i = this.a.families || [], o = this.a.testStrings || {}, r = new f;
            for (t = 0, n = a.length; t < n; t++)p(this.c, a[t], g(r));
            var s = [];
            for (t = 0, n = i.length; t < n; t++)if (a = i[t].split(":"), a[1])for (var l = a[1].split(","), d = 0; d < l.length; d += 1)s.push(new w(a[0], l[d])); else s.push(new w(a[0]));
            y(r, function () {
                e(s, o)
            })
        };
        var de = "https://fonts.googleapis.com/css", ce = {
            latin: "BESbswy",
            "latin-ext": "çöüğş",
            cyrillic: "йяЖ",
            greek: "αβΣ",
            khmer: "កខគ",
            Hanuman: "កខគ"
        }, ue = {
            thin: "1",
            extralight: "2",
            "extra-light": "2",
            ultralight: "2",
            "ultra-light": "2",
            light: "3",
            regular: "4",
            book: "4",
            medium: "5",
            "semi-bold": "6",
            semibold: "6",
            "demi-bold": "6",
            demibold: "6",
            bold: "7",
            "extra-bold": "8",
            extrabold: "8",
            "ultra-bold": "8",
            ultrabold: "8",
            black: "9",
            heavy: "9",
            l: "3",
            r: "4",
            b: "7"
        }, me = {
            i: "i",
            italic: "i",
            n: "n",
            normal: "n"
        }, _e = /^(thin|(?:(?:extra|ultra)-?)?light|regular|book|medium|(?:(?:semi|demi|extra|ultra)-?)?bold|black|heavy|l|r|b|[1-9]00)?(n|i|normal|italic)?$/, pe = {
            Arimo: !0,
            Cousine: !0,
            Tinos: !0
        };
        te.prototype.load = function (e) {
            var t = new f, n = this.c, a = new K(this.a.api, this.a.text), i = this.a.families;
            X(a, i);
            var o = new Q(i);
            ee(o), p(n, Z(a), g(t)), y(t, function () {
                e(o.a, o.c, pe)
            })
        }, ne.prototype.load = function (e) {
            var t = this.a.id, n = this.c.o;
            t ? h(this.c, (this.a.api || "https://use.typekit.net") + "/" + t + ".js", function (t) {
                if (t)e([]); else if (n.Typekit && n.Typekit.config && n.Typekit.config.fn) {
                    t = n.Typekit.config.fn;
                    for (var a = [], i = 0; i < t.length; i += 2)for (var o = t[i], r = t[i + 1], s = 0; s < r.length; s++)a.push(new w(o, r[s]));
                    try {
                        n.Typekit.load({events: !1, classes: !1, async: !0})
                    } catch (l) {
                    }
                    e(a)
                }
            }, 2e3) : e([])
        }, ae.prototype.load = function (e) {
            var t = this.f.id, n = this.c.o, a = this;
            t ? (n.__webfontfontdeckmodule__ || (n.__webfontfontdeckmodule__ = {}), n.__webfontfontdeckmodule__[t] = function (t, n) {
                for (var i = 0, o = n.fonts.length; i < o; ++i) {
                    var r = n.fonts[i];
                    a.a.push(new w(r.name, Y("font-weight:" + r.weight + ";font-style:" + r.style)))
                }
                e(a.a)
            }, h(this.c, (this.f.api || "https://f.fontdeck.com/s/css/js/") + _(this.c) + "/" + t + ".js", function (t) {
                t && e([])
            })) : e([])
        };
        var he = new J(window);
        he.a.c.custom = function (e, t) {
            return new $(t, e)
        }, he.a.c.fontdeck = function (e, t) {
            return new ae(t, e)
        }, he.a.c.monotype = function (e, t) {
            return new G(t, e)
        }, he.a.c.typekit = function (e, t) {
            return new ne(t, e)
        }, he.a.c.google = function (e, t) {
            return new te(t, e)
        };
        var fe = {load: r(he.load, he)};
        a = function () {
            return fe
        }.call(t, n, t, e), !(void 0 !== a && (e.exports = a))
    }()
}, function (e, t, n) {
    (function (t) {
        const a = n(7), i = (n(9), t("#templateListPanel")), o = n(162), r = n(2), s = "http://10.16.31.156:8888", l = [{
            base: "/",
            name: "基本",
            default_data: [
            /*{
            	path: "ycy", 
            	name: "1497358330640392.png"
            }, {path: "ycy",
                name: "1497341253433583.png"
            }, {path: "ycy", name: "qiaobusi.png"}, {path: "ycy", name: "1497349330186969.png"}, {
                path: "number",
                name: "0.png"
            }, {path: "number", name: "1.png"}, {path: "number", name: "2.png"}, {
                path: "number",
                name: "3.png"
            }, {path: "ycy", name: "1497361931431726.png"}, {path: "12.13", name: "y1.png"}, {
                path: "12.13",
                name: "y2.png"
            }, {path: "ycy", name: "square.png"}, {path: "ycy", name: "rect.png"}, {
                path: "number",
                name: "4.png"
            }, {path: "number", name: "5.png"}, {path: "number", name: "6.png"}, {
                path: "number",
                name: "7.png"
            }, {path: "number", name: "8.png"}, {path: "number", name: "9.png"}, {
                path: "ycy",
                name: "1497349308972378.png"
            }, {path: "ycy", name: "1497358415697318.png"}, {path: "number", name: "2018.png"}, {
                path: "number",
                name: "214.png"
            }, {path: "number", name: "521.png"}, {path: "number", name: "666.png"}, {
                path: "ycy",
                name: "15001138834243.png"
            }, {path: "ycy", name: "15012217440302.png"}, {path: "ycy", name: "149736081661197.png"}, {
                path: "ycy",
                name: "150124885876147.png"
            }, {path: "number", name: "a.png"}, {path: "number", name: "b.png"}, {
                path: "number",
                name: "c.png"
            }, {path: "number", name: "d.png"}, {path: "ycy", name: "150124886404799.png"}, {
                path: "ycy",
                name: "1497341648630817.png"
            }, {path: "ycy", name: "1500113899335699.png"}, {
                path: "ycy",
                name: "1497349318710157.png"
            }, {path: "number", name: "e.png"}, {path: "number", name: "f.png"}, {
                path: "number",
                name: "g.png"
            }, {path: "number", name: "h.png"}, {path: "ycy", name: "1497358341379675.png"}, {
                path: "ycy",
                name: "1497358350165468.png"
            }, {path: "ycy", name: "1497358361258349.png"}, {
                path: "ycy",
                name: "1497358369407856.png"
            }, {path: "number", name: "i.png"}, {path: "number", name: "j.png"}, {
                path: "number",
                name: "k.png"
            }, {path: "number", name: "l.png"}, {path: "ycy", name: "1497358394098337.png"}, {
                path: "ycy",
                name: "1497358402907191.png"
            }, {path: "ycy", name: "1497358424721668.png"}, {
                path: "ycy",
                name: "1497360791278381.png"
            }, {path: "number", name: "m.png"}, {path: "number", name: "n.png"}, {
                path: "number",
                name: "o.png"
            }, {path: "number", name: "p.png"}, {path: "ycy", name: "1497360799371587.png"}, {
                path: "ycy",
                name: "1497360806532410.png"
            }, {path: "ycy", name: "1497360824709676.png"}, {
                path: "ycy",
                name: "1497360831838506.png"
            }, {path: "number", name: "q.png"}, {path: "number", name: "r.png"}, {
                path: "number",
                name: "s.png"
            }, {path: "number", name: "t.png"}, {path: "ycy", name: "1497360842403148.png"}, {
                path: "ycy",
                name: "1497360854668377.png"
            }, {path: "ycy", name: "1497360861974239.png"}, {
                path: "ycy",
                name: "1497360869673379.png"
            }, {path: "number", name: "u.png"}, {path: "number", name: "v.png"}, {
                path: "number",
                name: "w.png"
            }, {path: "number", name: "x.png"}, {path: "number", name: "y.png"}, {
                path: "number",
                name: "z.png"
            }, {path: "12.13", name: "1c91db35c0d7ba4ec8c9ba788adf5e8a_t.jpg"}, {
                path: "12.13",
                name: "1e4fef377884794eec06829cb48beb34_t.jpg"
            }, {path: "12.13", name: "2d2fccc0b0dcbaa289780489a678065e_t.jpg"}, {
                path: "12.13",
                name: "3e76dd296c523b8286c4cf1d5563e03d_t.jpg"
            }, {path: "12.13", name: "4f8948a9b724d5348d3177d8db3e5198_t.jpg"}, {
                path: "12.13",
                name: "9a109e0fde8287d45c06e00ea8de9cf1_t.jpg"
            }, {path: "12.13", name: "14a884f18f598d5cca76c5e6eae85e1c_t.jpg"}, {
                path: "12.13",
                name: "79e33961767f1899d69254cc156b3dcc_t.jpg"
            }, {path: "12.13", name: "488a3b8d3fe76e8d95de1a76cb197d36_t.jpg"}, {
                path: "12.13",
                name: "1714ba8aaf85a4da1679d22b0f9313ef_t.jpg"
            }, {path: "12.13", name: "2812cdf19a965be838941a9f3e78aa9a_t.jpg"}, {
                path: "12.13",
                name: "5525cd0ea58c0c6a2573b35d42256470_t.jpg"
            }, {path: "12.13", name: "8242384e7a5e65d20012bf9f618fa437_t.jpg"}, {
                path: "12.13",
                name: "ab36c673c960dc5cdfcb510deec53bb5_t.jpg"
            }, {path: "12.13", name: "be3a7e8ce61a1db9af2b0c0a05905e39_t.jpg"}, {
                path: "12.13",
                name: "f69e1c25bd59a881c4eece81cf361768_t.jpg"
            }, {path: "12.13", name: "0ff5eb1c3084a8704d83866c53448eef_t.jpg"}, {
                path: "ycy",
                name: "1497360876505720.png"
            }, {path: "ycy", name: "1497361892210489.png"}, {path: "ycy", name: "1497361898274899.png"}, {
                path: "ycy",
                name: "1497361905632850.png"
            }, {path: "ycy", name: "1497361912997783.png"}, {path: "ycy", name: "1497361925956296.png"}, {
                path: "ycy",
                name: "1497361937553103.png"
            }, {path: "ycy", name: "1497419853766846.png"}, {path: "ycy", name: "1497420055495885.png"}, {
                path: "ycy",
                name: "1500113907072561.png"
            }, {path: "ycy", name: "1500217214545459.png"}, {path: "ycy", name: "1501248815511441.png"}, {
                path: "ycy",
                name: "1501248824072476.png"
            }, {path: "ycy", name: "1501248830490751.png"}, {path: "ycy", name: "1501248836152698.png"}, {
                path: "ycy",
                name: "1501248841785706.png"
            }, {path: "ycy", name: "1501248846976719.png"}, {path: "ycy", name: "1501248853208370.png"}, {
                path: "ycy",
                name: "1501248870100204.png"
            }, {path: "ycy", name: "1501248875587903.png"}, {path: "ycy", name: "1501248880900181.png"}, {
                path: "ycy",
                name: "1501248886815676.png"
            }, {path: "ycy", name: "mao1.png"}, {path: "ycy", name: "tuzi1.png"}*/
            ]
        }], d = function () {
            const e = function (e, t) {
                const n = [];
                return n.push('<li class="template-item" idx="' + t + '">'), n.push('  <div page="' + e.page + '" class="title-drop">' + e.name + '<i class="iconfont icon-unfold"></i></div>'), n.push('  <ul class="clearfix sticker-group fadeIn animated list_item_badges">'), n.push("  </ul>"), e.page && n.push('  <div class="page-list"><a>1</a><a class="next">下一页</a></div>'), n.push("</li>"), n.join("")
            }, t = [];
            for (var n = 0, a = l.length; n < a; n++)t.push(e(l[n], n));
            i.html(t.join("")), i.find(".template-item").eq(0).children(".title-drop").click()
        }, c = function (e, t) {
            const n = [], a = l[e.attr("idx")];
            var i = s;
            a.base && (i += a.base);
            const o = function (t) {
                for (var a = 0, o = t.length; a < o; a++)n.push('<li class="sticker-item animated flipInX">'), n.push('  <img src="' + i + t[a].path + "/" + t[a].name + '">'), n.push("</li>");
                e.find(".sticker-group").html(n.join("")), e.children(".title-drop").attr("over", !0)
            };
            a.default_data && o(a.default_data)
        }, u = function () {
            const e = {
                _1: "icons",
                _2: "insects",
                _3: "landscape-architecture",
                _4: "music",
                _5: "others",
                _6: "people",
                _7: "seasons-events",
                _8: "sports",
                _9: "transportation",
                _10: "woods-leaves",
                _11: "years-card"
            }, n = path+"/silhouette/";
            var i = window.location.hash;
            const s = t("#cache_shop_template_list");
            var l = null;
            if (i.indexOf("@+1") > 0 && i.indexOf("1+@") > 0 ? (l = i.substring(i.indexOf("@+1") + 3, i.indexOf("1+@")), window.location.hash = "") : l = r.get("cache-shop-template-list"), !l)return s.html('<li class="null-data"> <i class="iconfont icon-meiyougengduo"></i><br/> <div>还没有选择模板库中的素材，点击上方“去模板库选择”<br/>赶快去添加吧</div> </li>'), !0;
            t('[directto="template_cache_shop"]').click(), r.set("cache-shop-template-list", l), l = l.split("|"), console.log("1213:");
            const d = [];
            for (var c, u = 0, m = l.length; u < m; u++)c = l[u].replace("*^", "_t.jpeg"), c = c.split("*"), c = e["_" + c[0]] + "/" + c[1], d.push('<li><div class="item-image" _link="' + c + '" style="background-image: url(' + n + c + ');"></div></li>');
            return s.html(d.join("")), s.find("li .item-image").on("click", function () {
                return o.isLogin() ? o.isVip() ? void a.loadMaskImage(n + t(this).attr("_link")) : (window._hmt.push(["_trackEvent", "template", "shop-custom", "not-allow"]), window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'>成为VIP会员后即可快速使用模板中的素材<br/><span onclick='window.showVIP()' style='color:white;text-decoration:underline;'>查看详情</span></div>",
                    type: "error",
                    timeout: 3e4
                })) : window.Noti.notif({
                    msg: "<div style='line-height:22px;margin-top:10px;'><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                    type: "error",
                    timeout: 3e4
                })
            }), !1
        };
        e.exports = {
            init: function () {
                i.on("click", ".template-item .title-drop", function () {
                    const e = t(this), n = e.parent(".template-item");
                    n.hasClass("active") ? n.removeClass("active") : (n.addClass("active"), e.attr("over") || c(n))
                });
                u();
                const e = t("#img_list_left_preview");
                window.onFileFinish = function () {
                    if (!o.isLogin())return window._hmt.push(["_trackEvent", "template", "upload", "not-allow"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>注册登录即可使用自定义模版<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    const t = document.getElementById("upload_file").files[0], n = t.type;
                    if (!(n.indexOf("image") >= 0))return window._hmt.push(["_trackEvent", "template", "upload", "not-image"]), alert("需要插入图片"), !1;
                    var a = new FileReader;
                    a.onload = function (t) {
                        var n = t.target.result, a = new Image;
                        a.onload = function () {
                            a.width, a.height;
                            window._hmt.push(["_trackEvent", "template", "upload"])
                        }, a.src = n, e[0].src = n
                    }, a.readAsDataURL(t)
                }, e.click(function () {
                    window._hmt.push(["_trackEvent", "template", "upload", "preview"]), a.loadMaskImage(t(this).attr("src"))
                }), i.on("click", ".sticker-item img", function () {
                    const e = t(this).attr("src");
                    window._hmt.push(["_trackEvent", "template", "reload", e]), a.loadMaskImage(e)
                }), t("#left_panel_template").on("click", ".template-image-list li", function () {
                    var e = t(this).attr("key");
                    e.indexOf("http") < 0 && (e = domain + e), window._hmt.push(["_trackEvent", "template", "reload", e]), a.loadMaskImage(e)
                }), d()
            }
        }
    }).call(t, n(1))
}, function (e, t, n) {
    (function (t) {
        const a = (n(16), n(162)), i = n(9), o = t("#form_btn_submit"), r = t("#loginRegForm"), s = t("#form_btn_sendcode"), l = t("#modal_login_panel"), d = t("#modal_vip_panel"), c = t("#form_login_code"), u = n(168), m = t("#form_login_username"), _ = t("#form_login_verify"), p = t("#form_login_password"), h = t("#form_verify_time_span"), f = function (e) {
            window.Noti.notif({
                msg: "<div style='line-height:22px;margin-top:10px;'>" + e + "</div>",
                type: "error",
                timeout: 5e3
            })
        }, g = function (e, t) {
            return Math.floor(e + Math.random() * (t - e))
        };
        window.random = g;
        const y = g(1111, 9999);
        var v = 2;
        const b = function (e, t) {
            i.verifyCode(i.encryptCode({phone: e, email: t, code: y}, "verify-key-name"), function (e) {
                if (e.err)return f("发送时出现了一个错误！");
                window.Noti.notif({
                    msg: "发送成功！",
                    type: "success",
                    timeout: 5e3
                }), h.text("59"), s.removeClass("verify-loading");
                var t = 59, n = setInterval(function () {
                    if (t--, h.text(t), 0 == t)return clearInterval(n), void h.text("发送")
                }, 1e3)
            })
        }, w = function () {
            if ("发送" == h.text().trim()) {
                const e = m.val();
                _.val();
                var n, a;
                if (!/^1\d{10}$/.test(e))return f("您输入的手机号错误！");
                a = e, h.text("发送中"), 1 == v && c.hasClass("hide") ? b(a, n) : i.val(n, a, function (e) {
                    return "boolean" == typeof e && 1 == e ? (f("您输入的邮箱或者手机号已经注册过，可直接登录！"), h.text("发送"), r.removeClass("reg-form"), v = 2, s.removeClass("verify-loading"), o.text("登陆"), t("#zhanghu-reg").addClass("hide"), void t("#zhanghu-login").removeClass("hide")) : void b(a, n)
                })
            }
        }, M = t("#formLoading"), k = function (e) {
            e ? M.removeClass("hide") : M.addClass("hide")
        }, L = function () {
            const e = m.val(), t = p.val();
            var n, a;
            if (1 == v) {
                if (!/^1\d{10}$/.test(e))return f("您输入的手机号错误！");
                if (a = e, !t || t.length <= 5)return f("密码不能为空或密码的长度必须大于5位");
                const o = _.val();
                if (!o || !y || y != o)return f("您输入的验证码不正确！");
                s.addClass("verify-loading"), k(!0);
                const r = i.encryptCode({
                    phone: a,
                    email: n,
                    pwd: t,
                    password: u(t),
                    t: "yciyun",
                    yq: c.val() || "",
                    reset: c.hasClass("hide")
                }, "yeelogo-login-key");
                i.register(r, function (e) {
                    return k(!1), s.removeClass("verify-loading"), e.errno < 0 || e.errmsg ? f("发生了一个错误！") : void window.location.reload()
                })
            } else if (2 == v) {
                if (/^1\d{10}$/.test(e))a = e; else {
                    if (!/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(e))return f("您输入的手机号或者邮箱错误！");
                    n = e
                }
                if (!t || t.length <= 5)return f("密码不能为空或密码的长度必须大于5位");
                const r = i.encryptCode({
                    phone: a,
                    email: n,
                    pwd: t,
                    password: u(t),
                    t: "yciyun",
                    yq: c.val() || ""
                }, "yeelogo-login-key");
                k(!0), i.login(r, function (e) {
                    return k(!1), e.errno < 0 || e.errmsg || e.err ? f(e.errno == -2 ? "用户名或密码错误！" : "登陆错误！") : (window.location.reload(), void l.removeClass("modal-show"))
                })
            }
        };
        e.exports = {
            init: function () {
                t(".modal-panel").on("click", ".modal-close", function () {
                    t(this).parent(".modal-panel").parent(".my-modal").removeClass("modal-show")
                }), o.click(function () {
                    L()
                }), _.focus(function () {
                    "验证码" == t(this).val() && t(this).val("")
                }), _.blur(function () {
                    t(this).val() || t(this).val("验证码")
                });
                const e = t(".my-modal");
                window.showLogin = function () {
                    window._hmt.push(["_trackEvent", "modal", "login", "show"]), a.isLogin() || (e.removeClass("modal-show"), l.addClass("modal-show"))
                }, window.showVIP = function () {
                    window._hmt.push(["_trackEvent", "modal", "vip", "show"]), a.isVip() || (e.removeClass("modal-show"), d.addClass("modal-show"))
                }, t("#loginOutPanel").click(function () {
                    window._hmt.push(["_trackEvent", "user", "loginout"]), a.loginOut()
                }), s.click(function () {
                    window._hmt.push(["_trackEvent", "user", "sendverify"]), w()
                }), t('[directTo="zhanghu-login"]').click(function () {
                    r.removeClass("reg-form"), v = 2, o.text("登陆").css("background-color", "#f3643b"), m.attr("placeholder", "手机号或者邮箱")
                }), t('[directTo="zhanghu-reg"]').click(function () {
                    r.addClass("reg-form"), v = 1, "direct_reg" == t(this).attr("id") ? (o.text("注册").css("background-color", "#1a2637"), c.removeClass("hide")) : (o.text("重置并登录"), c.addClass("hide")), m.attr("placeholder", "手机号")
                })
            }
        }
    }).call(t, n(1))
}, function (e, t, n) {
    var a;
    !function (i) {
        "use strict";
        function o(e, t) {
            var n = (65535 & e) + (65535 & t), a = (e >> 16) + (t >> 16) + (n >> 16);
            return a << 16 | 65535 & n
        }

        function r(e, t) {
            return e << t | e >>> 32 - t
        }

        function s(e, t, n, a, i, s) {
            return o(r(o(o(t, e), o(a, s)), i), n)
        }

        function l(e, t, n, a, i, o, r) {
            return s(t & n | ~t & a, e, t, i, o, r)
        }

        function d(e, t, n, a, i, o, r) {
            return s(t & a | n & ~a, e, t, i, o, r)
        }

        function c(e, t, n, a, i, o, r) {
            return s(t ^ n ^ a, e, t, i, o, r)
        }

        function u(e, t, n, a, i, o, r) {
            return s(n ^ (t | ~a), e, t, i, o, r)
        }

        function m(e, t) {
            e[t >> 5] |= 128 << t % 32, e[(t + 64 >>> 9 << 4) + 14] = t;
            var n, a, i, r, s, m = 1732584193, _ = -271733879, p = -1732584194, h = 271733878;
            for (n = 0; n < e.length; n += 16)a = m, i = _, r = p, s = h, m = l(m, _, p, h, e[n], 7, -680876936), h = l(h, m, _, p, e[n + 1], 12, -389564586), p = l(p, h, m, _, e[n + 2], 17, 606105819), _ = l(_, p, h, m, e[n + 3], 22, -1044525330), m = l(m, _, p, h, e[n + 4], 7, -176418897), h = l(h, m, _, p, e[n + 5], 12, 1200080426), p = l(p, h, m, _, e[n + 6], 17, -1473231341), _ = l(_, p, h, m, e[n + 7], 22, -45705983), m = l(m, _, p, h, e[n + 8], 7, 1770035416), h = l(h, m, _, p, e[n + 9], 12, -1958414417), p = l(p, h, m, _, e[n + 10], 17, -42063), _ = l(_, p, h, m, e[n + 11], 22, -1990404162), m = l(m, _, p, h, e[n + 12], 7, 1804603682), h = l(h, m, _, p, e[n + 13], 12, -40341101), p = l(p, h, m, _, e[n + 14], 17, -1502002290), _ = l(_, p, h, m, e[n + 15], 22, 1236535329), m = d(m, _, p, h, e[n + 1], 5, -165796510), h = d(h, m, _, p, e[n + 6], 9, -1069501632), p = d(p, h, m, _, e[n + 11], 14, 643717713), _ = d(_, p, h, m, e[n], 20, -373897302), m = d(m, _, p, h, e[n + 5], 5, -701558691), h = d(h, m, _, p, e[n + 10], 9, 38016083), p = d(p, h, m, _, e[n + 15], 14, -660478335), _ = d(_, p, h, m, e[n + 4], 20, -405537848), m = d(m, _, p, h, e[n + 9], 5, 568446438), h = d(h, m, _, p, e[n + 14], 9, -1019803690), p = d(p, h, m, _, e[n + 3], 14, -187363961), _ = d(_, p, h, m, e[n + 8], 20, 1163531501), m = d(m, _, p, h, e[n + 13], 5, -1444681467), h = d(h, m, _, p, e[n + 2], 9, -51403784), p = d(p, h, m, _, e[n + 7], 14, 1735328473), _ = d(_, p, h, m, e[n + 12], 20, -1926607734), m = c(m, _, p, h, e[n + 5], 4, -378558), h = c(h, m, _, p, e[n + 8], 11, -2022574463), p = c(p, h, m, _, e[n + 11], 16, 1839030562), _ = c(_, p, h, m, e[n + 14], 23, -35309556), m = c(m, _, p, h, e[n + 1], 4, -1530992060), h = c(h, m, _, p, e[n + 4], 11, 1272893353), p = c(p, h, m, _, e[n + 7], 16, -155497632), _ = c(_, p, h, m, e[n + 10], 23, -1094730640), m = c(m, _, p, h, e[n + 13], 4, 681279174), h = c(h, m, _, p, e[n], 11, -358537222), p = c(p, h, m, _, e[n + 3], 16, -722521979), _ = c(_, p, h, m, e[n + 6], 23, 76029189), m = c(m, _, p, h, e[n + 9], 4, -640364487), h = c(h, m, _, p, e[n + 12], 11, -421815835), p = c(p, h, m, _, e[n + 15], 16, 530742520), _ = c(_, p, h, m, e[n + 2], 23, -995338651), m = u(m, _, p, h, e[n], 6, -198630844), h = u(h, m, _, p, e[n + 7], 10, 1126891415), p = u(p, h, m, _, e[n + 14], 15, -1416354905), _ = u(_, p, h, m, e[n + 5], 21, -57434055), m = u(m, _, p, h, e[n + 12], 6, 1700485571), h = u(h, m, _, p, e[n + 3], 10, -1894986606), p = u(p, h, m, _, e[n + 10], 15, -1051523), _ = u(_, p, h, m, e[n + 1], 21, -2054922799), m = u(m, _, p, h, e[n + 8], 6, 1873313359), h = u(h, m, _, p, e[n + 15], 10, -30611744), p = u(p, h, m, _, e[n + 6], 15, -1560198380), _ = u(_, p, h, m, e[n + 13], 21, 1309151649), m = u(m, _, p, h, e[n + 4], 6, -145523070), h = u(h, m, _, p, e[n + 11], 10, -1120210379), p = u(p, h, m, _, e[n + 2], 15, 718787259), _ = u(_, p, h, m, e[n + 9], 21, -343485551), m = o(m, a), _ = o(_, i), p = o(p, r), h = o(h, s);
            return [m, _, p, h]
        }

        function _(e) {
            var t, n = "", a = 32 * e.length;
            for (t = 0; t < a; t += 8)n += String.fromCharCode(e[t >> 5] >>> t % 32 & 255);
            return n
        }

        function p(e) {
            var t, n = [];
            for (n[(e.length >> 2) - 1] = void 0, t = 0; t < n.length; t += 1)n[t] = 0;
            var a = 8 * e.length;
            for (t = 0; t < a; t += 8)n[t >> 5] |= (255 & e.charCodeAt(t / 8)) << t % 32;
            return n
        }

        function h(e) {
            return _(m(p(e), 8 * e.length))
        }

        function f(e, t) {
            var n, a, i = p(e), o = [], r = [];
            for (o[15] = r[15] = void 0, i.length > 16 && (i = m(i, 8 * e.length)), n = 0; n < 16; n += 1)o[n] = 909522486 ^ i[n], r[n] = 1549556828 ^ i[n];
            return a = m(o.concat(p(t)), 512 + 8 * t.length), _(m(r.concat(a), 640))
        }

        function g(e) {
            var t, n, a = "0123456789abcdef", i = "";
            for (n = 0; n < e.length; n += 1)t = e.charCodeAt(n), i += a.charAt(t >>> 4 & 15) + a.charAt(15 & t);
            return i
        }

        function y(e) {
            return unescape(encodeURIComponent(e))
        }

        function v(e) {
            return h(y(e))
        }

        function b(e) {
            return g(v(e))
        }

        function w(e, t) {
            return f(y(e), y(t))
        }

        function M(e, t) {
            return g(w(e, t))
        }

        function k(e, t, n) {
            return t ? n ? w(t, e) : M(t, e) : n ? v(e) : b(e)
        }

        a = function () {
            return k
        }.call(t, n, t, e), !(void 0 !== a && (e.exports = a))
    }(this)
}, function (e, t, n) {
    (function (t) {
        function a(e, t) {
            var n = base64Img2Blob(e);
            window.navigator.msSaveBlob(n, t)
        }

        function i() {
            var e = navigator.userAgent;
            return e.indexOf("OPR") > -1 ? "Opera" : e.indexOf("Firefox") > -1 ? "FF" : e.indexOf("Trident") > -1 ? "IE" : e.indexOf("Edge") > -1 ? "Edge" : e.indexOf("Chrome") > -1 ? "Chrome" : e.indexOf("Safari") > -1 ? "Safari" : void 0
        }

        const o = n(162), r = n(9);
        t("#btn-save");
        t("#canvas");
        const s = t("#down_checkbox_pub")[0];
        var l = function (e, n) {
            var a = document.createElementNS("http://www.w3.org/1999/xhtml", "a");
            const i = t(a);
            i.attr(""), a.href = e, a.download = n;
            var o = document.createEvent("MouseEvents");
            o.initMouseEvent("click", !0, !1, window, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, null), a.dispatchEvent(o)
        };
        const d = i();
        e.exports = {
            init: function () {
                const e = t("#btn-preview"), n = t("#modal_download_panel"), i = t("#downloadPreviewPanel"), c = t(".my-modal"), u = t("#canvas");
                e.on("click", function () {
                    window._hmt.push(["_trackEvent", "modal", "download", "show"]), c.removeClass("modal-show"), n.addClass("modal-show"), s.checked = !0;
                    var e = u[0].toDataURL("image/png", .5);
                    i.css("background-image", "url(" + e + ")")
                });
                const m = t("#btn-save");
                m.on("click", function (e) {
                    if (!o.isLogin())return window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>注册登录即可下载图片<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    var i = t("#canvas")[0].toDataURL();
                    "IE" === d ? a(i, "易词云.png") ://!IE
                        l(i, "易词云.png"), setTimeout(function () {
                        if (n.removeClass("modal-show"), s.checked) {
                            const e = o.getUser();
                            r.uploadImage({
                                name: e.username + "-" + e.id + (new Date).getTime(),
                                k: i,
                                t: "yeelogo"
                            }, function (e) {
                                e.err ? window._hmt.push(["_trackEvent", "user-template", "upload", "failed"]) : r.uploadTemplate({url: e}, function (e) {
                                    e.err && window._hmt.push(["_trackEvent", "user-template", "write", "failed"])
                                }), window._hmt.push(["_trackEvent", "user-template", "download"])
                            })
                        }
                    }, 100)
                })
            }
        }
    }).call(t, n(1))
}, function (e, t, n) {
    (function (t) {
        const a = n(162), i = n(9), o = n(51);
        window.moment = o;
        const r = t("#pay_preview_image"), s = t("#payImageUploadPanel"), l = t("#myLoading"), d = t("#tbGridList");
        var c = null;
        const u = {_0: "管理员正在准备红包", _1: "已发红包", _2: "好友尚未开通"}, m = function (e, t, n) {
            return n ? (console.log("pay_url:", t), t && "-" != t ? u["_" + e] : '<a href="#" onclick="window.showHdModal()">您需上传收款码</a>') : "未开通永久VIP"
        }, _ = function () {
            d.html('<tr class="load-panel"> <td colspan="3"> <div><i class="iconfont icon-load"></i></div> </td> </tr>'), d.addClass("loading");
            a.getUser();
            i.getRecord(function (e) {
                if (d.removeClass("loading"), e.err || !e.list)return window.Noti.notif({
                    msg: "获取出错了",
                    type: "error",
                    timeout: 5e3
                });
                t("#invite_info_fee").text(e.fee || 0), t("#invite_info_count").text(e.count || 0);
                const n = e.list;
                if (n.length <= 0)return d.html('<tr class="none-panel"> <td colspan="3"> <div>还没有邀请申请记录</div> </td> </tr>');
                const i = a.getPayUrl(), o = [];
                for (var r, s = 0, l = n.length; s < l; s++)r = n[s], o.push("<tr>"), o.push("  <td>" + r.phone + "</td>"), o.push("  <td>" + m(r.s, i, r.vip) + "</td>"), o.push("</tr>");
                d.html(o.join("")), d.attr("over", !0)
            })
        };
        window.onWxPayImageFinish = function () {
            if (c = null, !a.isLogin())return window._hmt.push(["_trackEvent", "pay", "upload", "not-allow"]), window.Noti.notif({
                msg: "<div style='line-height:22px;margin-top:10px;'>注册登录即可参加好友邀请活动<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                type: "error",
                timeout: 3e4
            });
            const e = document.getElementById("pay_upload_file").files[0], t = e.type;
            if (!(t.indexOf("image") >= 0))return window._hmt.push(["_trackEvent", "pay", "upload", "not-image"]), window.Noti.notif({
                msg: "<div style='line-height:22px;margin-top:10px;'>需要插入图片</div>",
                type: "error",
                timeout: 3e3
            }), c = null, !1;
            var n = new FileReader;
            n.onload = function (e) {
                var t = e.target.result, n = new Image;
                n.onload = function () {
                    n.width, n.height;
                    window._hmt.push(["_trackEvent", "pay", "upload"])
                }, s.addClass("image-over"), n.src = t, c = t, r[0].src = t
            }, n.readAsDataURL(e)
        }, e.exports = {
            init: function () {
                t("#act_btn_kaitong").click(function () {
                    if (!a.isLogin())return window._hmt.push(["_trackEvent", "pay", "btn-upload", "not-allow"]), window.Noti.notif({
                        msg: "<div style='line-height:22px;margin-top:10px;'>注册登录即可参加好友邀请活动<br/><span onclick='window.showLogin()' style='color:white;text-decoration:underline;'>点我登录</span></div>",
                        type: "error",
                        timeout: 3e4
                    });
                    if (!c)return window.Noti.notif({msg: "需要上传一个你的收款二维码", type: "error", timeout: 3e3});
                    l.removeClass("hide");
                    const e = a.getUser();
                    i.uploadImage({
                        name: e.username + "-" + e.id + "-" + (new Date).getTime(),
                        k: c,
                        t: "yworlde"
                    }, function (n) {
                        return n.err ? (l.addClass("hide"), window.Noti.notif({
                            msg: "<div style='line-height:22px;margin-top:10px;'>" + n.err + "</div>",
                            type: "error",
                            timeout: 3e3
                        })) : void i.joinInvite({
                            uid: e.id,
                            phone: e.phone,
                            url: "http://oss.ymark.cc/" + n
                        }, function (e) {
                            l.addClass("hide"), t("#modal_hb_panel").removeClass("modal-show"), t("#sub_nav_trigger_invite_list").removeClass("hide"), t("#modal_hb_panel").addClass("modal_hd_over"), t("#invite_info_every_fee").text(a.isVip() ? "5" : "3"), window.location.reload()
                        })
                    })
                }), window.showHdModal = function () {
                    t("#modal_hb_panel").addClass("modal-show"), window._hmt.push(["_trackEvent", "campaign", "show"])
                }, t("#sub_nav_trigger_invite_list").click(function () {
                    d.attr("over") || _()
                });
                const e = t("#invite_add_form_panel");
                t("#invite_btn_show_add_panel").click(function () {
                    e.hasClass("hide") ? e.removeClass("hide") : e.addClass("hide")
                }), t("#invite_btn_close").click(function () {
                    e.addClass("hide")
                });
                const n = t("#form_input_invite_add_friend_phone");
                t("#invite_btn_add_record").click(function () {
                    const t = n.val();
                    if (!/^1\d{10}$/.test(t))return window.Noti.notif({msg: "您输入的手机号错误！", type: "error", timeout: 3e3});
                    e.addClass("loading");
                    const r = a.getUser();
                    i.insertRecord({uid: r.id, yq_phone: r.phone, byq_phone: t, vip: a.isVip ? 1 : 0}, function (a) {
                        return e.removeClass("loading"), a.err ? window.Noti.notif({
                            msg: a.err || "提交出错了！",
                            type: "error",
                            timeout: 3e3
                        }) : (e.addClass("hide"), window.Noti.notif({
                            msg: "<div style='line-height:22px;margin-top:10px;'>提交成功，管理员将收到信息<br/>确认没有问题后，您将最晚在今晚9点前收到红包</div>",
                            type: "success",
                            timeout: 15e3
                        }), n.val(""), void d.prepend("<tr><td>" + o().format("YYYY-MM-DD HH:mm:ss") + "</td><td>" + t + "</td><td>正在确认中</td></tr>"))
                    })
                })
            }
        }
    }).call(t, n(1))
}, function (e, t, n) {
    var a, i;
    (function (o) {
        !function (o, r) {
            a = r, i = "function" == typeof a ? a.call(t, n, t, e) : a, !(void 0 !== i && (e.exports = i))
        }(this, function () {
            function e(e) {
                var t = function () {
                    return a("<span>", {id: "notifIt_close", html: "&times"})
                }, n = function () {
                    var e = a("<div>", {id: "ui_notifIt"}), t = a("<p>", {html: d.msg});
                    //return e.append(t), e
                }, a = o, i = function () {
                    a("#ui_notifIt").remove(), clearTimeout(window.notifit_timeout)
                }, r = function () {
                    if (clearTimeout(window.notifit_timeout), d.fade)a("#ui_notifIt").fadeOut("slow", function () {
                        a("#ui_notifIt").remove(), d.callback && d.callback()
                    }); else {
                        if (d.animations && d.animations[d.animation] && d.animations[d.animation][d.position] && d.animations[d.animation][d.position].out && d.animations[d.animation][d.position].out.start && d.animations[d.animation][d.position].out.end)animation1 = d.animations[d.animation][d.position].out.start, animation2 = d.animations[d.animation][d.position].out.end; else {
                            if (!(d.animations[d.available_animations[0]] && d.animations[d.available_animations[0]][d.position] && d.animations[d.available_animations[0]][d.position].out && d.animations[d.available_animations[0]][d.position].out.start && d.animations[d.available_animations[0]][d.position].out.end))throw new Error("Invalid animation");
                            animation1 = d.animations[d.available_animations[0]][d.position].out.start, animation2 = d.animations[d.available_animations[0]][d.position].out.end
                        }
                        a("#ui_notifIt").animate(animation1, 100, function () {
                            a("#ui_notifIt").animate(animation2, 100, function () {
                                a("#ui_notifIt").remove(), d.callback && d.callback()
                            })
                        })
                    }
                };
                i(), window.notifit_timeout = null;
                var s = (window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth) / 2, l = ["left", "center", "right", "bottom"], d = {
                    type: "default",
                    width: 400,
                    height: 60,
                    position: "right",
                    autohide: 1,
                    msg: "This is my default message",
                    opacity: 1,
                    multiline: 0,
                    fade: 0,
                    bgcolor: "",
                    color: "",
                    timeout: 5e3,
                    zindex: null,
                    offset: 0,
                    callback: null,
                    clickable: !1,
                    animation: "slide"
                };
                if (a.extend(d, e), d.animations = {}, d.animations.slide = {
                        center: {
                            css_start: {
                                top: parseInt(0 - (d.height + 10)),
                                left: s - parseInt(d.width / 2)
                            },
                            "in": {top: parseInt(10 + d.offset)},
                            out: {
                                start: {top: parseInt(d.height - d.height / 2)},
                                end: {top: parseInt(0 - 2 * d.height)}
                            }
                        },
                        bottom: {
                            css_start: {
                                top: "auto",
                                bottom: parseInt(0 - (d.height + 10)),
                                left: s - parseInt(d.width / 2)
                            },
                            "in": {bottom: parseInt(10 + d.offset)},
                            out: {
                                start: {bottom: parseInt(d.height - d.height / 2)},
                                end: {bottom: parseInt(0 - 2 * d.height)}
                            }
                        },
                        right: {
                            css_start: {right: parseInt(0 - (d.width + 10)), right: parseInt(0 - 2 * d.width)},
                            "in": {right: parseInt(10 + d.offset)},
                            out: {
                                start: {right: parseFloat(d.width - .9 * d.width)},
                                end: {right: parseInt(0 - 2 * d.width)}
                            }
                        },
                        left: {
                            css_start: {left: parseInt(0 - (d.width + 10))},
                            "in": {left: parseInt(10 + d.offset)},
                            out: {
                                start: {left: parseFloat(d.width - .9 * d.width)},
                                end: {left: parseInt(0 - 2 * d.width)}
                            }
                        }
                    }, d.animations.zoom = {
                        center: {
                            css_start: {top: 10, left: s - parseInt(d.width / 2), zoom: .01},
                            "in": {zoom: 1},
                            out: {start: {zoom: 1.3}, end: {zoom: .01}}
                        },
                        bottom: {
                            css_start: {top: "auto", bottom: 10, left: s - parseInt(d.width / 2), zoom: .01},
                            "in": {zoom: 1},
                            out: {start: {zoom: 1.3}, end: {zoom: .01}}
                        },
                        right: {
                            css_start: {right: 10, zoom: .01},
                            "in": {right: parseInt(10 + d.offset), zoom: 1},
                            out: {start: {zoom: 1.3}, end: {zoom: .01}}
                        },
                        left: {
                            css_start: {left: 10, zoom: .01},
                            "in": {zoom: 1},
                            out: {start: {zoom: 1.3}, end: {zoom: .01}}
                        }
                    }, d.available_animations = Object.keys(d.animations), !d.available_animations.length)throw new Error("No animations");
                if (!l.length)throw new Error("No available positions");
                -1 === l.indexOf(d.position) && (d.position = l[0]), -1 === d.available_animations.indexOf(d.animation) && (d.animation = d.available_animations[0]), "function" != typeof d.callback && (d.callback = null), d.width > 0 ? d.width = d.width : "all" === d.width ? d.width = screen.width - 60 : d.width = 400, (d.height > 100 || d.height < 0) && (d.height = 60);
                var c = n();
                return d.clickable && c.append(t()), a("body").append(c), d.zindex && a("#ui_notifIt").css("z-index", d.zindex), d.multiline ? a("#ui_notifIt").css("padding", 15) : (a("#ui_notifIt").css("height", d.height), a("#ui_notifIt p").css("line-height", d.height + "px")), a("#ui_notifIt").css({
                    width: d.width,
                    opacity: d.opacity,
                    "background-color": d.bgcolor,
                    color: d.color
                }), a("#ui_notifIt").addClass(d.type), d.animations[d.animation][d.position].css_start ? a("#ui_notifIt").css(d.animations[d.animation][d.position].css_start) : a("#ui_notifIt").css(d.animations[d.available_animations[0]][d.position].css_start), a("#ui_notifIt").animate(d.animations[d.animation][d.position]["in"]), d.clickable || a("#ui_notifIt").click(function (e) {
                    e.stopPropagation(), r(d)
                }), a("body").on("click", "#ui_notifIt #notifIt_close", function () {
                    r(d)
                }), d.autohide && (isNaN(d.timeout) || (window.notifit_timeout = setTimeout(function () {
                    r(d)
                }, d.timeout))), {destroy: i, dismiss: r}
            }

            function t(e) {
                function t() {
                    if (null !== c)return c;
                    var e = s("<button>", {
                        "class": "notifit_confirm_accept",
                        text: d.textaccept
                    }), t = s("<button>", {
                        "class": "notifit_confirm_cancel",
                        text: d.textcancel
                    }), n = s("<div>", {"class": "notifit_confirm_message", text: d.message});
                    return c = s("<div>", {"class": "notifit_confirm"}), c.append(n).append(e).append(t), u = s("<div>", {"class": "notifit_confirm_bg"}), c
                }

                function n() {
                    c && (d.fullscreen ? (u.hide(), c.hide(), s("body").append(u), s("body").append(c), c.css({
                        top: u.outerHeight() / 2 - c.outerHeight() / 2,
                        left: u.outerWidth() / 2 - c.outerWidth() / 2
                    }), u.fadeIn("fast", function () {
                        c.slideDown("fast")
                    })) : (c.css({
                        top: "20px",
                        left: "auto",
                        right: "20px",
                        display: "none"
                    }), s("body").append(c), c.slideDown("fast")))
                }

                function a() {
                    c && c.slideUp("fast", function () {
                        c.remove()
                    }), u && u.fadeOut("fast", function () {
                        u.remove()
                    })
                }

                function i() {
                    a();
                    var e = null;
                    return s(this).hasClass("notifit_confirm_accept") ? e = !0 : s(this).hasClass("notifit_confirm_cancel") && (e = !1), "function" == typeof d.callback ? d.callback(e) : e
                }

                function r() {
                    s("html").one("click", ".notifit_confirm_accept, .notifit_confirm_cancel", i)
                }

                var s = o, l = {
                    textaccept: "Accept",
                    textcancel: "Cancel",
                    message: "Is that what you want to do?",
                    fullscreen: !1,
                    callback: null
                }, d = s.extend({}, l, e), c = s(".notifit_confirm")[0] ? s(".notifit_confirm") : null, u = s(".notifit_confirm_bg")[0] ? s(".notifit_confirm_bg") : null;
                t(), n(), r()
            }

            function n(e) {
                function t() {
                    if (null !== c)return c;
                    var e = s("<button>", {
                        "class": "notifit_prompt_accept",
                        text: d.textaccept
                    }), t = s("<button>", {
                        "class": "notifit_prompt_cancel",
                        text: d.textcancel
                    }), n = s("<p>", {
                        "class": "notifit_prompt_message",
                        text: d.message
                    }), a = s("<input>", {type: "text", "class": "notifit_prompt_input", value: d.default_value});
                    return c = s("<div>", {"class": "notifit_prompt"}), c.append(n).append(a).append(t).append(e), u = s("<div>", {"class": "notifit_prompt_bg"}), c
                }

                function n() {
                    c && (d.fullscreen ? (u.hide(), c.hide(), s("body").append(u), s("body").append(c), c.css({
                        top: u.outerHeight() / 2 - c.outerHeight() / 2,
                        left: u.outerWidth() / 2 - c.outerWidth() / 2
                    }), u.fadeIn("fast", function () {
                        c.slideDown("fast", function () {
                            s(this).find(".notifit_prompt_input").focus()
                        })
                    })) : (c.css({
                        top: "20px",
                        left: "auto",
                        right: "20px",
                        display: "none"
                    }), s("body").append(c), c.slideDown("fast", function () {
                        s(this).find(".notifit_prompt_input").focus()
                    })))
                }

                function a() {
                    c && c.slideUp("fast", function () {
                        c.remove()
                    }), u && u.fadeOut("fast", function () {
                        u.remove()
                    })
                }

                function i() {
                    a();
                    var e = null;
                    return s(this).hasClass("notifit_prompt_accept") ? e = s(this).parents(".notifit_prompt").find(".notifit_prompt_input").val() : s(this).hasClass("notifit_prompt_cancel") && (e = !1), "function" == typeof d.callback ? d.callback(e) : e
                }

                function r() {
                    s("html").one("click", ".notifit_prompt_accept, .notifit_prompt_cancel", i)
                }

                var s = o, l = {
                    message: "Tell me something",
                    default_value: "",
                    textaccept: "Accept",
                    textcancel: "Cancel",
                    fullscreen: !1,
                    callback: null
                }, d = s.extend({}, l, e), c = s(".notifit_prompt")[0] ? s(".notifit_prompt") : null, u = s(".notifit_prompt_bg")[0] ? s(".notifit_prompt_bg") : null;
                t(), n(), r()
            }

            return {notif: e, notif_confirm: t, notif_prompt: n}
        })
    }).call(t, n(1))
}, function (e, t, n) {
    var a, i, o;
    /*!
     *         ,/
     *       ,'/
     *     ,' /
     *   ,'  /_____,
     * .'____    ,'
     *      /  ,'
     *     / ,'
     *    /,'
     *   /'
     *
     * Selectric ϟ v1.13.0 (Aug 22 2017) - http://lcdsantos.github.io/jQuery-Selectric/
     *
     * Copyright (c) 2017 Leonardo Santos; MIT License
     *
     */
    !function (r) {
        i = [n(1)], a = r, o = "function" == typeof a ? a.apply(t, i) : a, !(void 0 !== o && (e.exports = o))
    }(function (e) {
        "use strict";
        var t = e(document), n = e(window), a = "selectric", i = "Input Items Open Disabled TempShow HideSelect Wrapper Focus Hover Responsive Above Below Scroll Group GroupLabel", o = ".sl", r = ["a", "e", "i", "o", "u", "n", "c", "y"], s = [/[\xE0-\xE5]/g, /[\xE8-\xEB]/g, /[\xEC-\xEF]/g, /[\xF2-\xF6]/g, /[\xF9-\xFC]/g, /[\xF1]/g, /[\xE7]/g, /[\xFD-\xFF]/g], l = function (t, n) {
            var a = this;
            a.element = t, a.$element = e(t), a.state = {
                multiple: !!a.$element.attr("multiple"),
                enabled: !1,
                opened: !1,
                currValue: -1,
                selectedIdx: -1,
                highlightedIdx: -1
            }, a.eventTriggers = {
                open: a.open,
                close: a.close,
                destroy: a.destroy,
                refresh: a.refresh,
                init: a.init
            }, a.init(n)
        };
        l.prototype = {
            utils: {
                isMobile: function () {
                    return /android|ip(hone|od|ad)/i.test(navigator.userAgent)
                }, escapeRegExp: function (e) {
                    return e.replace(/[.*+?^${}()|[\]\\]/g, "\\$&")
                }, replaceDiacritics: function (e) {
                    for (var t = s.length; t--;)e = e.toLowerCase().replace(s[t], r[t]);
                    return e
                }, format: function (e) {
                    var t = arguments;
                    return ("" + e).replace(/\{(?:(\d+)|(\w+))\}/g, function (e, n, a) {
                        return a && t[1] ? t[1][a] : t[n]
                    })
                }, nextEnabledItem: function (e, t) {
                    for (; e[t = (t + 1) % e.length].disabled;);
                    return t
                }, previousEnabledItem: function (e, t) {
                    for (; e[t = (t > 0 ? t : e.length) - 1].disabled;);
                    return t
                }, toDash: function (e) {
                    return e.replace(/([a-z0-9])([A-Z])/g, "$1-$2").toLowerCase()
                }, triggerCallback: function (t, n) {
                    var i = n.element, o = n.options["on" + t], r = [i].concat([].slice.call(arguments).slice(1));
                    e.isFunction(o) && o.apply(i, r), e(i).trigger(a + "-" + this.toDash(t), r)
                }, arrayToClassname: function (t) {
                    var n = e.grep(t, function (e) {
                        return !!e
                    });
                    return e.trim(n.join(" "))
                }
            }, init: function (t) {
                var n = this;
                if (n.options = e.extend(!0, {}, e.fn[a].defaults, n.options, t), n.utils.triggerCallback("BeforeInit", n), n.destroy(!0), n.options.disableOnMobile && n.utils.isMobile())return void(n.disableOnMobile = !0);
                n.classes = n.getClassNames();
                var i = e("<input/>", {
                    "class": n.classes.input,
                    readonly: n.utils.isMobile()
                }), o = e("<div/>", {
                    "class": n.classes.items,
                    tabindex: -1
                }), r = e("<div/>", {"class": n.classes.scroll}), s = e("<div/>", {
                    "class": n.classes.prefix,
                    html: n.options.arrowButtonMarkup
                }), l = e("<span/>", {"class": "label"}), d = n.$element.wrap("<div/>").parent().append(s.prepend(l), o, i), c = e("<div/>", {"class": n.classes.hideselect});
                n.elements = {
                    input: i,
                    items: o,
                    itemsScroll: r,
                    wrapper: s,
                    label: l,
                    outerWrapper: d
                }, n.options.nativeOnMobile && n.utils.isMobile() && (n.elements.input = void 0, c.addClass(n.classes.prefix + "-is-native"), n.$element.on("change", function () {
                    n.refresh()
                })), n.$element.on(n.eventTriggers).wrap(c), n.originalTabindex = n.$element.prop("tabindex"), n.$element.prop("tabindex", -1), n.populate(), n.activate(), n.utils.triggerCallback("Init", n)
            }, activate: function () {
                var e = this, t = e.elements.items.closest(":visible").children(":hidden").addClass(e.classes.tempshow), n = e.$element.width();
                t.removeClass(e.classes.tempshow), e.utils.triggerCallback("BeforeActivate", e), e.elements.outerWrapper.prop("class", e.utils.arrayToClassname([e.classes.wrapper, e.$element.prop("class").replace(/\S+/g, e.classes.prefix + "-$&"), e.options.responsive ? e.classes.responsive : ""])), e.options.inheritOriginalWidth && n > 0 && e.elements.outerWrapper.width(n), e.unbindEvents(), e.$element.prop("disabled") ? (e.elements.outerWrapper.addClass(e.classes.disabled), e.elements.input && e.elements.input.prop("disabled", !0)) : (e.state.enabled = !0, e.elements.outerWrapper.removeClass(e.classes.disabled), e.$li = e.elements.items.removeAttr("style").find("li"), e.bindEvents()), e.utils.triggerCallback("Activate", e)
            }, getClassNames: function () {
                var t = this, n = t.options.customClass, a = {};
                return e.each(i.split(" "), function (e, i) {
                    var o = n.prefix + i;
                    a[i.toLowerCase()] = n.camelCase ? o : t.utils.toDash(o)
                }), a.prefix = n.prefix, a
            }, setLabel: function () {
                var t = this, n = t.options.labelBuilder;
                if (t.state.multiple) {
                    var a = e.isArray(t.state.currValue) ? t.state.currValue : [t.state.currValue];
                    a = 0 === a.length ? [0] : a;
                    var i = e.map(a, function (n) {
                        return e.grep(t.lookupItems, function (e) {
                            return e.index === n
                        })[0]
                    });
                    i = e.grep(i, function (t) {
                        return i.length > 1 || 0 === i.length ? "" !== e.trim(t.value) : t
                    }), i = e.map(i, function (a) {
                        return e.isFunction(n) ? n(a) : t.utils.format(n, a)
                    }), t.options.multiple.maxLabelEntries && (i.length >= t.options.multiple.maxLabelEntries + 1 ? (i = i.slice(0, t.options.multiple.maxLabelEntries), i.push(e.isFunction(n) ? n({text: "..."}) : t.utils.format(n, {text: "..."}))) : i.slice(i.length - 1)), t.elements.label.html(i.join(t.options.multiple.separator))
                } else {
                    var o = t.lookupItems[t.state.currValue];
                    t.elements.label.html(e.isFunction(n) ? n(o) : t.utils.format(n, o))
                }
            }, populate: function () {
                var t = this, n = t.$element.children(), a = t.$element.find("option"), i = a.filter(":selected"), o = a.index(i), r = 0, s = t.state.multiple ? [] : 0;
                i.length > 1 && t.state.multiple && (o = [], i.each(function () {
                    o.push(e(this).index())
                })), t.state.currValue = ~o ? o : s, t.state.selectedIdx = t.state.currValue, t.state.highlightedIdx = t.state.currValue, t.items = [], t.lookupItems = [], n.length && (n.each(function (n) {
                    var a = e(this);
                    if (a.is("optgroup")) {
                        var i = {element: a, label: a.prop("label"), groupDisabled: a.prop("disabled"), items: []};
                        a.children().each(function (n) {
                            var a = e(this);
                            i.items[n] = t.getItemData(r, a, i.groupDisabled || a.prop("disabled")), t.lookupItems[r] = i.items[n], r++
                        }), t.items[n] = i
                    } else t.items[n] = t.getItemData(r, a, a.prop("disabled")), t.lookupItems[r] = t.items[n], r++
                }), t.setLabel(), t.elements.items.append(t.elements.itemsScroll.html(t.getItemsMarkup(t.items))))
            }, getItemData: function (t, n, a) {
                var i = this;
                return {
                    index: t,
                    element: n,
                    value: n.val(),
                    className: n.prop("class"),
                    text: n.html(),
                    slug: e.trim(i.utils.replaceDiacritics(n.html())),
                    alt: n.attr("data-alt"),
                    selected: n.prop("selected"),
                    disabled: a
                }
            }, getItemsMarkup: function (t) {
                var n = this, a = "<ul>";
                return e.isFunction(n.options.listBuilder) && n.options.listBuilder && (t = n.options.listBuilder(t)), e.each(t, function (t, i) {
                    void 0 !== i.label ? (a += n.utils.format('<ul class="{1}"><li class="{2}">{3}</li>', n.utils.arrayToClassname([n.classes.group, i.groupDisabled ? "disabled" : "", i.element.prop("class")]), n.classes.grouplabel, i.element.prop("label")), e.each(i.items, function (e, t) {
                        a += n.getItemMarkup(t.index, t)
                    }), a += "</ul>") : a += n.getItemMarkup(i.index, i)
                }), a + "</ul>"
            }, getItemMarkup: function (t, n) {
                var a = this, i = a.options.optionsItemBuilder, o = {
                    value: n.value,
                    text: n.text,
                    slug: n.slug,
                    index: n.index
                };
                return a.utils.format('<li data-index="{1}" class="{2}">{3}</li>', t, a.utils.arrayToClassname([n.className, t === a.items.length - 1 ? "last" : "", n.disabled ? "disabled" : "", n.selected ? "selected" : ""]), e.isFunction(i) ? a.utils.format(i(n, this.$element, t), n) : a.utils.format(i, o))
            }, unbindEvents: function () {
                var e = this;
                e.elements.wrapper.add(e.$element).add(e.elements.outerWrapper).add(e.elements.input).off(o)
            }, bindEvents: function () {
                var t = this;
                t.elements.outerWrapper.on("mouseenter" + o + " mouseleave" + o, function (n) {
                    e(this).toggleClass(t.classes.hover, "mouseenter" === n.type), t.options.openOnHover && (clearTimeout(t.closeTimer), "mouseleave" === n.type ? t.closeTimer = setTimeout(e.proxy(t.close, t), t.options.hoverIntentTimeout) : t.open())
                }), t.elements.wrapper.on("click" + o, function (e) {
                    t.state.opened ? t.close() : t.open(e)
                }), t.options.nativeOnMobile && t.utils.isMobile() || (t.$element.on("focus" + o, function () {
                    t.elements.input.focus()
                }), t.elements.input.prop({
                    tabindex: t.originalTabindex,
                    disabled: !1
                }).on("keydown" + o, e.proxy(t.handleKeys, t)).on("focusin" + o, function (e) {
                    t.elements.outerWrapper.addClass(t.classes.focus), t.elements.input.one("blur", function () {
                        t.elements.input.blur()
                    }), t.options.openOnFocus && !t.state.opened && t.open(e)
                }).on("focusout" + o, function () {
                    t.elements.outerWrapper.removeClass(t.classes.focus)
                }).on("input propertychange", function () {
                    var n = t.elements.input.val(), a = new RegExp("^" + t.utils.escapeRegExp(n), "i");
                    clearTimeout(t.resetStr), t.resetStr = setTimeout(function () {
                        t.elements.input.val("")
                    }, t.options.keySearchTimeout), n.length && e.each(t.items, function (e, n) {
                        if (!n.disabled) {
                            if (a.test(n.text) || a.test(n.slug))return void t.highlight(e);
                            if (n.alt)for (var i = n.alt.split("|"), o = 0; o < i.length && i[o]; o++)if (a.test(i[o].trim()))return void t.highlight(e)
                        }
                    })
                })), t.$li.on({
                    mousedown: function (e) {
                        e.preventDefault(), e.stopPropagation()
                    }, click: function () {
                        return t.select(e(this).data("index")), !1
                    }
                })
            }, handleKeys: function (t) {
                var n = this, a = t.which, i = n.options.keys, o = e.inArray(a, i.previous) > -1, r = e.inArray(a, i.next) > -1, s = e.inArray(a, i.select) > -1, l = e.inArray(a, i.open) > -1, d = n.state.highlightedIdx, c = o && 0 === d || r && d + 1 === n.items.length, u = 0;
                if (13 !== a && 32 !== a || t.preventDefault(), o || r) {
                    if (!n.options.allowWrap && c)return;
                    o && (u = n.utils.previousEnabledItem(n.lookupItems, d)), r && (u = n.utils.nextEnabledItem(n.lookupItems, d)), n.highlight(u)
                }
                return s && n.state.opened ? (n.select(d), void(n.state.multiple && n.options.multiple.keepMenuOpen || n.close())) : void(l && !n.state.opened && n.open())
            }, refresh: function () {
                var e = this;
                e.populate(), e.activate(), e.utils.triggerCallback("Refresh", e)
            }, setOptionsDimensions: function () {
                var e = this, t = e.elements.items.closest(":visible").children(":hidden").addClass(e.classes.tempshow), n = e.options.maxHeight, a = e.elements.items.outerWidth(), i = e.elements.wrapper.outerWidth() - (a - e.elements.items.width());
                !e.options.expandToItemText || i > a ? e.finalWidth = i : (e.elements.items.css("overflow", "scroll"), e.elements.outerWrapper.width(9e4), e.finalWidth = e.elements.items.width(), e.elements.items.css("overflow", ""), e.elements.outerWrapper.width("")), e.elements.items.width(e.finalWidth).height() > n && e.elements.items.height(n), t.removeClass(e.classes.tempshow)
            }, isInViewport: function () {
                var e = this;
                if (e.options.forceRenderAbove === !0)e.elements.outerWrapper.addClass(e.classes.above); else if (e.options.forceRenderBelow === !0)e.elements.outerWrapper.addClass(e.classes.below); else {
                    var t = n.scrollTop(), a = n.height(), i = e.elements.outerWrapper.offset().top, o = e.elements.outerWrapper.outerHeight(), r = i + o + e.itemsHeight <= t + a, s = i - e.itemsHeight > t, l = !r && s, d = !l;
                    e.elements.outerWrapper.toggleClass(e.classes.above, l), e.elements.outerWrapper.toggleClass(e.classes.below, d)
                }
            }, detectItemVisibility: function (t) {
                var n = this, a = n.$li.filter("[data-index]");
                n.state.multiple && (t = e.isArray(t) && 0 === t.length ? 0 : t, t = e.isArray(t) ? Math.min.apply(Math, t) : t);
                var i = a.eq(t).outerHeight(), o = a[t].offsetTop, r = n.elements.itemsScroll.scrollTop(), s = o + 2 * i;
                n.elements.itemsScroll.scrollTop(s > r + n.itemsHeight ? s - n.itemsHeight : o - i < r ? o - i : r)
            }, open: function (n) {
                var i = this;
                return (!i.options.nativeOnMobile || !i.utils.isMobile()) && (i.utils.triggerCallback("BeforeOpen", i), n && (n.preventDefault(), i.options.stopPropagation && n.stopPropagation()), void(i.state.enabled && (i.setOptionsDimensions(), e("." + i.classes.hideselect, "." + i.classes.open).children()[a]("close"), i.state.opened = !0, i.itemsHeight = i.elements.items.outerHeight(), i.itemsInnerHeight = i.elements.items.height(), i.elements.outerWrapper.addClass(i.classes.open), i.elements.input.val(""), n && "focusin" !== n.type && i.elements.input.focus(), setTimeout(function () {
                        t.on("click" + o, e.proxy(i.close, i)).on("scroll" + o, e.proxy(i.isInViewport, i))
                    }, 1), i.isInViewport(), i.options.preventWindowScroll && t.on("mousewheel" + o + " DOMMouseScroll" + o, "." + i.classes.scroll, function (t) {
                        var n = t.originalEvent, a = e(this).scrollTop(), o = 0;
                        "detail" in n && (o = n.detail * -1), "wheelDelta" in n && (o = n.wheelDelta), "wheelDeltaY" in n && (o = n.wheelDeltaY), "deltaY" in n && (o = n.deltaY * -1), (a === this.scrollHeight - i.itemsInnerHeight && o < 0 || 0 === a && o > 0) && t.preventDefault()
                    }), i.detectItemVisibility(i.state.selectedIdx), i.highlight(i.state.multiple ? -1 : i.state.selectedIdx), i.utils.triggerCallback("Open", i))))
            }, close: function () {
                var e = this;
                e.utils.triggerCallback("BeforeClose", e), t.off(o), e.elements.outerWrapper.removeClass(e.classes.open), e.state.opened = !1, e.utils.triggerCallback("Close", e)
            }, change: function () {
                var t = this;
                t.utils.triggerCallback("BeforeChange", t), t.state.multiple ? (e.each(t.lookupItems, function (e) {
                    t.lookupItems[e].selected = !1, t.$element.find("option").prop("selected", !1)
                }), e.each(t.state.selectedIdx, function (e, n) {
                    t.lookupItems[n].selected = !0, t.$element.find("option").eq(n).prop("selected", !0)
                }), t.state.currValue = t.state.selectedIdx, t.setLabel(), t.utils.triggerCallback("Change", t)) : t.state.currValue !== t.state.selectedIdx && (t.$element.prop("selectedIndex", t.state.currValue = t.state.selectedIdx).data("value", t.lookupItems[t.state.selectedIdx].text), t.setLabel(), t.utils.triggerCallback("Change", t))
            }, highlight: function (e) {
                var t = this, n = t.$li.filter("[data-index]").removeClass("highlighted");
                t.utils.triggerCallback("BeforeHighlight", t), void 0 === e || e === -1 || t.lookupItems[e].disabled || (n.eq(t.state.highlightedIdx = e).addClass("highlighted"), t.detectItemVisibility(e), t.utils.triggerCallback("Highlight", t))
            }, select: function (t) {
                var n = this, a = n.$li.filter("[data-index]");
                if (n.utils.triggerCallback("BeforeSelect", n, t), void 0 !== t && t !== -1 && !n.lookupItems[t].disabled) {
                    if (n.state.multiple) {
                        n.state.selectedIdx = e.isArray(n.state.selectedIdx) ? n.state.selectedIdx : [n.state.selectedIdx];
                        var i = e.inArray(t, n.state.selectedIdx);
                        i !== -1 ? n.state.selectedIdx.splice(i, 1) : n.state.selectedIdx.push(t), a.removeClass("selected").filter(function (t) {
                            return e.inArray(t, n.state.selectedIdx) !== -1
                        }).addClass("selected")
                    } else a.removeClass("selected").eq(n.state.selectedIdx = t).addClass("selected");
                    n.state.multiple && n.options.multiple.keepMenuOpen || n.close(), n.change(), n.utils.triggerCallback("Select", n, t)
                }
            }, destroy: function (e) {
                var t = this;
                t.state && t.state.enabled && (t.elements.items.add(t.elements.wrapper).add(t.elements.input).remove(), e || t.$element.removeData(a).removeData("value"), t.$element.prop("tabindex", t.originalTabindex).off(o).off(t.eventTriggers).unwrap().unwrap(), t.state.enabled = !1)
            }
        }, e.fn[a] = function (t) {
            return this.each(function () {
                var n = e.data(this, a);
                n && !n.disableOnMobile ? "string" == typeof t && n[t] ? n[t]() : n.init(t) : e.data(this, a, new l(this, t))
            })
        }, e.fn[a].defaults = {
            onChange: function (t) {
                e(t).change()
            },
            maxHeight: 300,
            keySearchTimeout: 500,
            arrowButtonMarkup: '<b class="button">&#x25be;</b>',
            disableOnMobile: !1,
            nativeOnMobile: !0,
            openOnFocus: !0,
            openOnHover: !1,
            hoverIntentTimeout: 500,
            expandToItemText: !1,
            responsive: !1,
            preventWindowScroll: !0,
            inheritOriginalWidth: !1,
            allowWrap: !0,
            forceRenderAbove: !1,
            forceRenderBelow: !1,
            stopPropagation: !0,
            optionsItemBuilder: "{text}",
            labelBuilder: "{text}",
            listBuilder: !1,
            keys: {
                previous: [37, 38],
                next: [39, 40],
                select: [9, 13, 27],
                open: [13, 32, 37, 38, 39, 40],
                close: [9, 27]
            },
            customClass: {prefix: a, camelCase: !1},
            multiple: {separator: ", ", keepMenuOpen: !0, maxLabelEntries: !1}
        }
    })
}, function (e, t, n) {
    var a = n(174);
    "string" == typeof a && (a = [[e.id, a, ""]]);
    n(6)(a, {});
    a.locals && (e.exports = a.locals)
}, function (e, t, n) {
    t = e.exports = n(5)(), t.push([e.id, '#formLoading{background:rgba(0,0,0,.56)}#formLoading .loader-panel{margin-top:-22.5px;margin-left:-22.5px}#formLoading .loader-panel .iconfont{font-size:50px;color:#fff;font-weight:700}#formLoading .loader-panel .iconfont,.icon-load{display:inline-block;-webkit-animation:rotating 1.2s linear infinite;animation:rotating 1.2s linear infinite}.icon-load{-webkit-font-smoothing:antialiased}#app-body{position:fixed;top:0;left:0;width:100%;height:100%;border-top:5px solid #192537;z-index:2}#main{min-width:1000px;top:0;left:0}#canvas-container,#main{overflow:hidden}#canvas-container{text-align:center}#canvas-container .canvas{display:inline-block}#canvas-container.transparent,iVBORw0KGgoAAAANSUhEUgAAACgAAAAoBAMAAAB+0KVeAAAAD1BMVEX////u7u7v7+/7+/vy8vI0zw7tAAAANklEQVQoz2NAAEVBGBgVpIagoBA2QSwqmRWFsAg6IVTCWSoGmCYpChngNx6hEqtDhEYFqSAIAKRZHH7tB4V9AAAAAElFTkSuQmCC\')}.config-item{position:relative;padding:20px 0;border-bottom:1px dashed hsla(0,0%,55%,.33)}.config-item .item-label{display:inline-block;width:60px;float:left}.config-item .slider-panel{padding:20px 10px;display:inline-block}.slider-item .item-label{margin-top:17px}.pick-color-item{width:60px;border-style:solid;border-width:2px;padding:5px}.radio-panel>label{padding:0 5px;font-size:12px}.radio-panel>label>input{margin-right:5px;position:relative;top:.5px}.pay-panel{margin-top:10px}.pay-panel>div{margin-top:20px;margin-bottom:5px;text-align:center}.pay-panel .text{font-size:14px;line-height:24px}.pay-panel>ul{margin-bottom:20px}.pay-panel>ul li{float:left;border-bottom:1.5px solid hsla(0,0%,71%,.79);cursor:pointer;padding:5px 15px}.pay-panel>ul .active{border-bottom-color:#3b99fc}#nav{left:0;bottom:0;width:70px;background:#1a1a20;font-family:Microsoft Yahei,noto_sansregular,Arial,SongTi,MingLiU,Abril Fatface,Arimo,Trocchi,sans-serif;position:absolute;top:0;z-index:13}#nav #logo{text-align:center;margin-top:10px;margin-bottom:15px}#nav #logo img{width:48px}#nav #nav-wrapper{position:absolute;width:100%;height:100%}#nav #nav-wrapper #nav-top{position:absolute;top:16px}#nav #nav-wrapper .sub-nav-note{position:relative;display:block;width:70px;height:44px;padding:15px 0 11px;font-family:Microsoft Yahei,noto_sansregular,Arial,SongTi,MingLiU,Abril Fatface,Arimo,Trocchi,sans-serif;font-size:12px;line-height:14px;color:#96969a;text-align:center;cursor:pointer}#nav #nav-wrapper .sub-nav-yq{height:17px;color:#ea3a1f}#nav #nav-wrapper .icon-title{margin-top:10px}#nav #nav-wrapper .nav-bottom{position:absolute;bottom:8px;left:0;right:0;text-align:center;color:hsla(0,0%,100%,.6);font-family:Microsoft Yahei,noto_sansregular,Arial,SongTi,MingLiU,Abril Fatface,Arimo,Trocchi,sans-serif;background:#1a1a21}#nav #nav-wrapper .nav-bottom .login-status .login-panel{display:block}#nav #nav-wrapper .nav-bottom .login-status .over-panel,#nav #nav-wrapper .nav-bottom .over-status .login-panel{display:none}#nav #nav-wrapper .nav-bottom .over-status .over-panel{display:block}#nav #nav-wrapper .nav-bottom-item{cursor:pointer;line-height:25px}#nav #nav-wrapper .sub-nav-note:hover{background:#26262b}#nav #nav-wrapper .active,#nav #nav-wrapper .active:hover{background-color:#ebebf0}#toolbar{left:70px;width:346px;box-shadow:2px 0 2px hsla(0,5%,67%,.32);position:absolute;top:0;bottom:0;background:#ebebf0;z-index:12;-webkit-transform:translateX(-346px);transform:translateX(-346px);-webkit-transition:-webkit-transform .2s ease;transition:transform .2s ease,-webkit-transform .2s ease}#toolbar .btn-reload-panel{position:absolute;width:80px;height:80px;border-radius:50%;top:50%;right:-40px;margin-top:-40px;text-align:center;background:#f3643b;box-shadow:0 0 5px #000;cursor:pointer}#toolbar .btn-reload-panel>div{position:relative;width:100%;height:100%}#toolbar .btn-reload-panel .iconfont{font-size:53px;position:absolute;top:0;left:0;width:80px;height:80px;line-height:71px;text-align:center;-webkit-transition:all .2s ease-in-out;transition:all .2s ease-in-out}#toolbar .btn-reload-panel span{line-height:80px;font-size:14px;color:#fff}#toolbar .btn-reload-panel.loading .iconfont{display:inline-block;-webkit-animation:rotating 1.2s linear infinite;animation:rotating 1.2s linear infinite}#toolbar.toolbar-show{-webkit-transform:translateX(0);transform:translateX(0)}#toolbar .toolbar-list{position:absolute;top:16px;bottom:16px;left:16px;right:16px}.input-item{text-align:left}.input-item .input-normal{-webkit-transition:background-color .15s cubic-bezier(.2,.3,0,1);transition:background-color .15s cubic-bezier(.2,.3,0,1);display:inline-block;padding:5px 10px;font-size:14px;line-height:21px;color:#333;background-color:#fff;background-image:none;border:1px solid #ccc;border-radius:4px;box-shadow:inset 0 1px 1px rgba(0,0,0,.075);-webkit-transition:border-color .15s ease-in-out,-webkit-box-shadow .15s ease-in-out;-webkit-transition:border-color .15s ease-in-out,box-shadow .15s ease-in-out;transition:border-color .15s ease-in-out,box-shadow .15s ease-in-out}.input-item .input-normal:focus{border-color:#ffab02;outline:0;box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(255,171,2,.6)}.dataset_rule{text-align:left}.dataset_rule li{padding:2px}.tabs-panel{color:#fff;display:-ms-flexbox;display:-webkit-box;display:flex;margin-bottom:15px}.tabs-panel>li{cursor:pointer;border-bottom:1.5px solid rgba(51,51,51,.2);padding:13px 0;color:rgba(51,51,51,.4);-ms-flex:1;-webkit-box-flex:1;flex:1;text-align:center}.tabs-panel>li.active{border-color:#f3643b;color:#f3643b}#toolbar .toolbar-list .scrollarea-content{position:relative;text-align:center;width:100%;height:100%}#toolbar .toolbar-list .scrollarea-content .textarea-panel{position:absolute;left:0;top:110px;bottom:0;right:0;display:block}#toolbar .toolbar-list .scrollarea-content .textarea-panel>div{position:relative;width:100%;height:100%}#toolbar .toolbar-list .scrollarea-content .textarea-panel>div .content-table-panel{position:absolute;overflow:auto;left:0;right:0;top:24px;bottom:0}#toolbar .toolbar-list .scrollarea-content .textarea-panel #input-list,#toolbar .toolbar-list .scrollarea-content .textarea-panel #jieba-input-list{width:98%;height:100%;border:0;outline:none;font-size:14px;padding-left:2%}#toolbar .toolbar-list .scrollarea-content #dataset_jiebasplit .gelatin-btn{padding:5px 10px;font-size:12px}#toolbar .toolbar-list .scrollarea-content #dataset_jiebasplit .textarea-panel{top:130px}#toolbar .toolbar-list .nav-container{width:100%;height:100%}#toolbar .toolbar-list .nav-container .tabs-container{position:absolute;top:100px;bottom:0;left:0;right:0;overflow:auto}#toolbar .toolbar-list .template-image-list{margin-left:-4px;margin-right:-4px;margin-top:3px}#toolbar .toolbar-list .template-image-list>li{width:96px;float:left;margin-right:5px;margin-left:6px;margin-bottom:7px;height:96px;background-color:#fff;background-size:contain;background-position:50%;background-repeat:no-repeat;border-radius:5px;box-shadow:0 0 2px rgba(36,207,218,.46);overflow:hidden;cursor:pointer}#toolbar .toolbar-list .scrollarea-content .title-drop{position:relative;padding:11px 20px;line-height:20px;font-size:12px;color:#000;background:#f7f7fa;font-family:Microsoft YaHei,noto_sansregular,sans-serif;overflow:hidden;cursor:pointer;border-bottom:hsla(0,0%,90%,.51);text-align:left}.tool-panel-component .sticker-group .sticker-item>img{max-height:100%;max-width:100%}@-webkit-keyframes rotating{0%{-webkit-transform:rotate(0deg);transform:rotate(0deg)}to{-webkit-transform:rotate(-1turn);transform:rotate(-1turn)}}@keyframes rotating{0%{-webkit-transform:rotate(0deg);transform:rotate(0deg)}to{-webkit-transform:rotate(-1turn);transform:rotate(-1turn)}}.table-handle-panel{width:100%;font-size:12px;border:1px solid #ccc;border-bottom:0;position:relative;height:100%}.table-handle-panel th{text-align:left;padding:5px;position:relative}.table-handle-panel th .icon-sort{position:absolute;font-size:12px;color:#6b6b6b;top:4px;right:4px}.table-handle-panel [data-sort]{cursor:pointer}.table-handle-panel th{background-color:rgba(51,51,51,.12);border-bottom:1px solid rgba(0,0,0,.15)}.table-handle-panel td:nth-child(1),.table-handle-panel th:nth-child(1){width:35px}.table-handle-panel td:nth-child(2) .text,.table-handle-panel th:nth-child(2) .text{max-width:100px}.table-handle-panel td:nth-child(3),.table-handle-panel th:nth-child(3){width:80px}.table-handle-panel td:nth-child(4),.table-handle-panel td:nth-child(5),.table-handle-panel th:nth-child(4),.table-handle-panel th:nth-child(5){width:35px}.table-handle-panel td{text-align:left;position:relative;background-color:#fff;border-bottom:1px solid #ccc;height:24px;padding:0 5px}.table-handle-panel td .text{z-index:1;line-height:24px;margin-left:2px;margin-right:2px}.table-handle-panel td .input-text{display:none;z-index:2;position:absolute;top:-1px;left:6px;right:2px;bottom:-1px;border:2px solid #5191f7}.table-handle-panel td .input{width:100%;height:100%;border:0;outline:none}.table-handle-panel .active .text{display:none}.table-handle-panel .active .input-text{display:block}.family-panel{display:inline-block;width:273px}.family-panel .selectric>span.label{font-family:iconfont!important;font-size:12px;font-style:normal}.family-panel .selectric-scroll li{position:relative}.family-panel .selectric-scroll .font-img{height:25px;overflow:hidden}.family-panel .selectric-scroll .font-img>img{margin-top:-20px}.family-panel .selectric-scroll .font-text{padding-left:11px;font-size:14px}.family-panel .selectric-scroll .font-vip:before{content:"*";display:inline-block;position:absolute;left:10px;top:14px;color:red}.font-loading .selectric>span.label:after{content:"\\E6A9";display:inline-block;margin-left:10px;-webkit-font-smoothing:antialiased;-webkit-animation:rotating 1.2s linear infinite;animation:rotating 1.2s linear infinite;font-size:14px}#toolbar .toolbar-list .scrollarea-content .tool-panel-item{margin-top:10px;display:none}#toolbar .toolbar-list .scrollarea-content .tool-panel-item.active{display:block}#toolbar .toolbar-list .scrollarea-content .tool-header{position:absolute;top:0;left:0;width:100%;height:100px;display:block}#toolbar .toolbar-list .scrollarea-content .title-drop{position:relative;padding:11px 20px;line-height:20px;font-size:12px;color:#000;background:#f7f7fa;font-family:Microsoft YaHei,noto_sansregular,sans-serif;overflow:hidden;cursor:pointer;border-bottom:hsla(0,0%,90%,.51);-webkit-transition:all .1s linear 0s;transition:all .1s linear 0s;text-align:left}#toolbar .toolbar-list .scrollarea-content .title-drop:hover,#toolbar .toolbar-list .scrollarea-content:focus{background:#44444a;color:#fff}#toolbar .toolbar-list .scrollarea-content .title-drop.title-drop-active{color:#fff;background:#44444a;box-shadow:inset 0 0 0 1px hsla(0,0%,78%,0);-webkit-box-shadow:0 0 0 1px hsla(0,0%,78%,0) inset;-moz-box-shadow:0 0 0 1px hsla(0,0%,78%,0) inset}.tool-panel-text .tool-item{padding:10px;cursor:pointer}.sticker-group .sticker-item:hover{background:hsla(0,0%,86%,.4);cursor:pointer}.tool-panel-text .tool-item:hover{background:#dddde3}.tool-panel-component .sticker-group{display:none;padding:10px;background-size:cover}.tool-panel-component .sticker-group.active{display:block}.tool-panel-component .sticker-group .sticker-item{position:relative;float:left;cursor:pointer;-webkit-transition:all .2s!important;transition:all .2s!important;width:50px;height:50px;padding:7px;margin:3px}.tool-panel-component>ul>li{-webkit-transition:all .2s ease-in-out;transition:all .2s ease-in-out;margin-bottom:10px;box-shadow:0 0 2px rgba(0,0,0,.21);margin-left:1px;margin-right:1px;border-radius:2px;overflow:hidden;background-color:#fff}.tool-panel-component>ul>li .loading-item{-webkit-transition:all .2s ease-in-out;transition:all .2s ease-in-out;display:none;position:absolute;top:50%;left:50%;margin-top:-30px;margin-left:-23px}.tool-panel-component>ul>li .sticker-item .item-like{position:absolute;top:0;height:17px;width:17px;border-radius:20px;right:0;background:#3091f2;display:none;text-align:center;color:#fff;line-height:17px}.tool-panel-component>ul>li .sticker-item .item-like .iconfont{font-size:12px}.tool-panel-component>ul>li .sticker-item:hover .item-like{display:block}.tool-panel-component>ul .sticker-group{height:340px;position:relative}.tool-panel-component>ul .none-data{position:absolute;left:50%;top:50%;margin-left:-35px;margin-top:-40px}.tool-panel-component>ul .none-data .icon-meiyougengduo{font-size:60px;color:#333;display:block}.tool-panel-component>ul>li.active .page-list,.tool-panel-component>ul>li.active .sticker-group{display:block}.tool-panel-component>ul>li.active .icon-unfold{-webkit-transform:rotate(180deg);transform:rotate(180deg)}.tool-panel-component>ul>li.active-loading .loading-item{display:block!important}.tool-panel-component>ul .list_item_base_line{background-color:#3f4652}.tool-panel-component>ul .list_item_base_line .sticker-item{width:auto;float:none;padding-top:19px;height:30px}.tool-panel-component .page-list{margin:10px 0;display:none}.tool-panel-component .page-list>a{width:32px;height:32px;display:inline-block;line-height:32px;text-align:center;border:1px solid red;-webkit-transition:all .2s ease-in-out;transition:all .2s ease-in-out;font-family:Arial;cursor:pointer;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none;border:1px solid #d7dde4;margin-right:4px;border-radius:4px}.tool-panel-component .page-list>a.active{background-color:#39f;border-color:#39f;color:#fff}.tool-panel-component .page-list .next{width:60px}.tool-panel-component .page-list>a:hover{border-color:#39f}.title-drop .iconfont{float:right;-webkit-transition:all .2s ease-in-out;transition:all .2s ease-in-out}.title-drop .icon-qunawanvip{color:#f3643b;margin-right:10px;margin-top:-14px;font-size:17px}.title-drop::hover{background:rgba(247,247,250,.87)}hr{border-top:1px solid hsla(0,2%,71%,.19);margin:10px 0;border-style:inset;border-left:0;border-right:0;border-bottom:0;border-top:1px solid #ddd;border-bottom:1px solid #fff;height:0;overflow:hidden}.toolbar-color-panel .tool-panel-component>ul>li.active .sticker-group{height:auto}.image-add-panel{position:relative;height:100px;border:2px dashed rgba(56,56,56,.72);border-radius:2px}.image-add-panel .import-box{position:absolute;cursor:pointer;left:0;bottom:0;width:100%;top:0;padding-top:12px;line-height:35px;z-index:2}.image-add-panel .import-box .iconfont{margin-right:5px;font-size:14px;position:relative;top:1px}.image-add-panel .file-button{position:absolute;top:0;left:0;right:0;bottom:0;opacity:0;z-index:3;cursor:pointer;width:100%}.ResourceImage-body .ResourceImage-item{box-sizing:border-box;position:relative;cursor:pointer;float:left;background-color:#fff;width:100%;line-height:90px}.ResourceImage-body .ResourceImage-item>img{max-height:100%;width:100%;vertical-align:middle}.ResourceImage-body .ResourceImage-item .ResourceImage-deleteIcon{width:30px;height:30px;border-radius:15px;line-height:30px;text-align:center;background:#222;color:#fff;position:absolute;right:10px;bottom:10px;display:none}.vip-notifit-panel h2{font-size:16px;border-bottom:6e solid #e2e2e26e;padding:5px}.vip-notifit-panel>ul li{text-align:left;font-size:12px;margin-bottom:10px;list-style-type:decimal;margin-left:14px}.vip-notifit-panel .ts{margin-top:20px;display:inline-block;font-size:12px;color:rgba(4,12,4,.76);text-decoration:underline}.modal-login-panel{position:absolute;top:0;left:0;width:100%;height:100%;-webkit-font-smoothing:antialiased;-webkit-tap-highlight-color:transparent}.modal-login-panel .top-info{padding-top:20px;padding-bottom:10px;width:100%;text-align:left}.modal-login-panel .top-info .title{margin:0 20px;text-align:center;font-size:15px;padding-bottom:10px}.modal-login-panel .top-info .title>a{color:#fff}.modal-login-panel .top-info>img{width:90px;opacity:.8;margin-top:20px}.modal-login-panel .top-info .left-vip{width:230px;float:left}.modal-login-panel .top-info .left-vip>p{margin-left:20px;margin-top:10px}.modal-login-panel .top-info .left-vip>ul{margin-top:5px}.modal-login-panel .top-info .left-vip>ul li{list-style-type:disc;margin-left:38px}.modal-login-panel .form-panel{text-align:center;width:300px;margin:0 auto;margin-top:20px}.modal-login-panel .form-panel input{width:274px;height:20px;padding:10px;font-size:14px;line-height:20px;color:#333;border:0;background:#fff;outline:none;font-family:FuturaMediumBT,Arial,Helvetica,sans-serif;box-shadow:0 0 2px rgba(0,0,0,.4);border-radius:4px;margin-top:8px}.modal-login-panel .form-panel .login-input{position:relative}.modal-login-panel .form-panel .gelatin-btn{margin-top:45px;width:200px}.modal-login-panel .form-panel .reg-btn-0{background-color:#24cfda}.modal-login-panel .form-panel .verify-input{width:50px;padding-left:3px;padding-right:3px;font-size:11px;position:relative;text-align:center;top:-1px}.modal-login-panel .form-panel .send-verify-btn{width:50px;padding:8px 5px;font-size:11px;position:relative;height:24px;line-height:23px}.modal-login-panel .form-panel .send-verify-btn .iconfont{opacity:0;width:0}.modal-login-panel .form-panel .verify-loading .iconfont{opacity:1;width:auto}.modal-login-panel .form-panel #form_login_code,.modal-login-panel .form-panel .send-verify-btn,.modal-login-panel .form-panel .verify-input{display:none}.modal-login-panel .reg-form .gelatin-btn{margin-top:15px}.modal-login-panel .reg-form .name-input{width:150px}.modal-login-panel .reg-form #form_login_code,.modal-login-panel .reg-form .send-verify-btn,.modal-login-panel .reg-form .verify-input{display:inline-block}.modal-login-panel .none-info{font-size:12px;margin-top:5px}.modal-login-panel .other-login{margin-top:20px}.modal-login-panel .other-login hr{border-top:none;border-bottom:1px solid #efefef;box-shadow:0 1px 0 1px #fff;margin:0}.modal-login-panel .other-login .user_other_login{background:#fafafa;padding:0 2em;position:relative;top:-10px}.modal-login-panel .other-login .iconfont{font-size:40px;margin:5px;width:40px;height:40px;display:inline-block}.modal-login-panel .loading-panel{text-align:center}.modal-login-panel .loading-panel .iconfont{font-size:100px;margin-top:140px;display:inline-block;font-weight:700;color:#0c6}.modal-login-panel .loading-panel .icon-load{color:#39f}.modal-login-panel .icon-load{display:inline-block;-webkit-animation:rotating 1.2s linear infinite;animation:rotating 1.2s linear infinite}#yqPanel{background:#000;line-height:20px;padding-top:6px;padding-bottom:3px}#yqPanel #yqvalue{text-decoration:underline;color:hsla(0,0%,100%,.76)}.chongzhi-panel,.friend-panel{text-align:center}.chongzhi-panel>p,.friend-panel>p{text-align:left;font-size:12px}.chongzhi-panel>img,.friend-panel>img{width:100px}.friend-panel [setyq]{font-size:30px;font-weight:700;background:gray;color:#fff;padding:0 7px}#left_panel_data .data-select-panel>div{float:left}#left_panel_data .data-select-panel .color-btn-panel{float:right;height:33px}#left_panel_data .data-select-panel .color-btn-panel>.gelatin-btn{height:20px;line-height:20px;font-size:12px;display:block;width:50px;margin:0;padding:0 3px}#left_panel_data .data-select-panel .color-btn-panel #btn_order_color{margin-top:3px}#left_panel_data .data-select-panel .text{line-height:40px}#left_panel_data .data-select-panel .selectric .button,#left_panel_data .data-select-panel .selectric .label{line-height:26px;height:26px}#left_panel_data .data-select-panel .select-theme{width:254px}#left_panel_data .data-select-panel .select-theme .selectric-items li{padding:5px}#left_panel_data .data-select-panel .input-panel{height:20px}#left_panel_data .data-select-panel .input-panel .input-color-item{display:inline-block;float:left;width:46.7px}#left_panel_data .data-select-panel .color-group-item{display:-webkit-box;display:-ms-flexbox;display:flex}#left_panel_data .data-select-panel .color-group-item>span{-webkit-box-flex:1;-ms-flex:1;flex:1;height:24px;display:inline-block}#left_panel_tips>ul{position:absolute;top:0;bottom:0;overflow:auto;left:0;right:0}#left_panel_tips li{text-align:left;margin-bottom:20px}#left_panel_tips li .title{background:#f7f7fa;padding:10px;cursor:pointer;display:block;text-decoration:underline}#left_panel_tips li .title:before{content:">";margin-right:5px;text-decoration:none;display:inline-block}#left_panel_tips li .content{background:#fff;padding:10px;border-top:1px dashed hsla(0,0%,50%,.27);display:none}#left_panel_tips li .content .iconfont{color:#ff6300;font-weight:700;font-size:16px;margin:0 5px}#left_panel_tips li .content a{text-decoration:underline;color:#466aff}#left_panel_tips li .content>p{margin:5px;display:block;padding:3px}#left_panel_tips li .content .way{font-weight:700;font-size:13px;color:rgba(0,85,255,.69)}#left_panel_tips li .content .att{margin-top:8px;border-top:1px dashed hsla(0,0%,50%,.27);padding-top:8px}#left_panel_tips li .active{display:block}.background-transparent{border:1px solid #000;display:inline-block;position:relative;margin-left:10px}#expect_word_panel{position:absolute;background:#ebebf0;left:0;width:100%;top:50px;bottom:0}#expect_word_panel>div{width:100%;height:100%;position:relative}#expect_word_panel .main-type{width:115px;position:absolute;top:0;height:100%;left:0;overflow:auto;border-right:1px solid rgba(0,0,0,.09)}#expect_word_panel .main-type li{text-align:left;padding:5px 0;border-bottom:1px dashed hsla(0,0%,50%,.36);position:relative}#expect_word_panel .main-type li>input{position:relative;top:1px}#expect_word_panel .main-type li>span{cursor:pointer;position:absolute;top:0;right:0;bottom:0;left:20px;line-height:24px}#expect_word_panel .main-type li.active>span,#expect_word_panel .main-type li:hover>span{background:#fff}#expect_word_panel .child-type-list li{text-align:left;position:relative;height:20px;line-height:20px}#expect_word_panel .child-type-list li>label{cursor:pointer;position:absolute;top:0;bottom:0;left:0;right:0}#expect_word_panel .child-type-list li>label input{margin-right:5px}#expect_word_panel .btn-panel{position:absolute;height:50px;left:120px;right:0;bottom:0}#expect_word_panel .content-list{position:absolute;top:0;left:120px;bottom:60px;right:0;overflow:auto}hr{max-width:400px;height:1px;margin-top:-1px;border:none;background-image:-webkit-linear-gradient(bottom,transparent,#d5d5d5,transparent);background-image:linear-gradient(0deg,transparent,#d5d5d5,transparent);background-image:-webkit-linear-gradient(0deg,transparent,#d5d5d5,transparent)}.modal-act-panel .title{font-size:16px;font-weight:700;text-align:center;margin-top:10px;padding:10px;border:2px dashed rgba(255,171,2,.26);border-radius:5px;background:#fdefef;color:#f4633b}.modal-act-panel .pay-panel{float:left;margin:0}.modal-act-panel .pay-panel>a{font-size:12px;text-align:center;display:block;text-decoration:underline;color:#3b71c7}.modal-act-panel .image-add-panel{text-align:center;width:100px;height:138px;margin:0}.modal-act-panel .image-add-panel .preview-image{display:none}.modal-act-panel .image-add-panel .preview-image>img{width:100%;height:100%}.modal-act-panel .image-over .import-box{display:none}.modal-act-panel .image-over .preview-image{display:block}.modal-act-panel .step-list{margin:10px}.modal-act-panel .step-list li{margin-top:10px}.modal-act-panel .step-list li>span{font-weight:700}.modal-act-panel .step-list li .sl{color:#000;font-weight:400;background:#e4e4e4}.modal-act-panel .step-list li hr{margin-top:10px;margin-bottom:10px}.modal-act-panel .right-panel{text-align:center;margin-left:120px}.modal-act-panel .right-panel .gelatin-btn{margin-bottom:10px}.modal-act-panel .right-panel .tips{display:none}.modal-act-panel .right-panel>ul li{text-align:left;font-size:12px;margin:0;border-bottom:1px dashed hsla(0,0%,69%,.47);list-style-type:disc;margin-left:15px}.modal-act-panel .right-panel>ul nth:last-child{border:0}.active-hb{position:fixed;bottom:20px;right:20px;z-index:22;width:80px;cursor:pointer}.active-hb>img{-webkit-animation-name:shake-little;animation-name:shake-little;-webkit-animation-duration:3s;animation-duration:3s;-webkit-animation-timing-function:ease-in-out;animation-timing-function:ease-in-out;-webkit-animation-iteration-count:infinite;animation-iteration-count:infinite}.active-hb>div{color:#fff;font-weight:700}#left_panel_invite_list .id-tips{background:#fff;margin-bottom:5px;padding:5px;font-size:13px}#left_panel_invite_list .id-tips>span{color:#000;background:#dedede;padding:0 5px}#left_panel_invite_list .show-panel{margin-bottom:4px}#left_panel_invite_list .show-panel>div{width:48%;background:#fff;float:left;padding:10px 0;border-radius:4px;box-shadow:0 1px 1px rgba(0,0,0,.05)}#left_panel_invite_list .show-panel>div .iconfont{font-size:30px}#left_panel_invite_list .show-panel>div>div{margin-top:10px;color:gray}#left_panel_invite_list .show-panel>div>div>span{color:#000;font-size:23px;padding:0 5px}#left_panel_invite_list .show-panel .block{margin-top:5px;width:100%}#left_panel_invite_list .add-panel{position:relative}#left_panel_invite_list .add-panel #invite_btn_show_add_panel{font-size:12px;text-decoration:underline;cursor:pointer;padding:10px}#left_panel_invite_list .add-panel #invite_btn_show_add_panel span{font-size:14px;color:#2d8bf0;margin-bottom:10px;display:block}#left_panel_invite_list .add-panel .form-panel{position:absolute;height:135px;left:0;right:0;background:#fff;z-index:222;top:56px;border-bottom-left-radius:4px;border-bottom-right-radius:4px;box-shadow:0 1px 1px rgba(0,0,0,.05);border:1px solid transparent}#left_panel_invite_list .add-panel .form-panel>div{padding:5px}#left_panel_invite_list .add-panel .form-panel>div .input{-webkit-transition:border .2s linear,box-shadow .2s linear;transition:border .2s linear,box-shadow .2s linear;padding:4px 6px;border:1px solid #ccc;background-color:#fff;box-shadow:inset 0 1px 1px rgba(0,0,0,.075);border-radius:3px;font-size:13px;line-height:20px;color:#555}#left_panel_invite_list .add-panel .form-panel #invite_btn_add_record{background-color:#2d8bf0}#left_panel_invite_list .add-panel .form-panel .gelatin-btn{font-size:13px}#left_panel_invite_list .add-panel .form-panel .load-panel{display:none}#left_panel_invite_list .add-panel .form-panel.loading .load-panel{display:block;position:absolute;left:0;right:0;background:#fff;top:0;height:100%;padding:0}#left_panel_invite_list .title{font-size:14px;padding:10px}#left_panel_invite_list .tblist-panel{position:absolute;top:218px;bottom:10px;overflow:auto;left:0;right:0}#left_panel_invite_list .tblist{width:100%}#left_panel_invite_list .tblist th{font-weight:700;text-align:center;font-size:12px;padding:3px}#left_panel_invite_list .tblist td{border-bottom:1px dashed hsla(0,0%,64%,.47);padding:3px}#left_panel_invite_list .tblist .load-panel{display:none}#left_panel_invite_list .tblist .none-panel td{border:0}#left_panel_invite_list .tblist .none-panel td>div{margin-top:20px}#left_panel_invite_list .tblist .loading tr{display:none}#left_panel_invite_list .tblist .loading .load-panel{display:table-row}#left_panel_invite_list .tblist .loading .load-panel td{border:0}#left_panel_invite_list .tblist .loading .load-panel td .icon-load{font-size:20px;margin-top:10px}.modal_hd_over .modal-act-panel #act_btn_kaitong,.modal_hd_over .modal-act-panel .pay-panel{display:none}.modal_hd_over .modal-act-panel .right-panel{margin-left:0}.modal_hd_over .modal-act-panel .right-panel .tips{display:block;color:red;padding-bottom:12px}.rotate-picker-item{margin-left:64px;margin-top:10px;border:1px dashed gray;padding:5px}.rotate-picker-item>label{margin-right:5px;margin-top:3px;display:inline-block}.rotate-picker-item [type=text]{width:100%;padding:4px 0;border-radius:3px;border:0;outline:none}.rotate-picker-item [type=checkbox]{margin-right:4px}.float-drop-color-pick-panel{position:fixed;top:0;left:0;z-index:9999;background:#000;width:74px;height:27px;display:none;opacity:0}.float-drop-color-pick-panel.active{display:block}#collorpicker_2{display:none!important}#collorpicker_2.active{display:block!important}#mobileSupport{width:100%;height:100%;position:fixed;top:0;left:0;bottom:0;right:0;z-index:1000000;background-color:#fff}#mobileSupport .tip-info{position:absolute;width:100%;height:40px;top:50%;margin-top:-30px;text-align:center;font-size:14px;line-height:20px;font-family:PingFangSC-Regular,Roboto,Verdana,Open Sans,Helvetica Neue,Helvetica,Hiragino Sans GB,Microsoft YaHei,Source Han Sans CN,WenQuanYi Micro Hei,Arial,sans-serif;-webkit-font-smoothing:antialiased;-webkit-tap-highlight-color:transparent}.local-font-panel{position:relative;height:50px;border:2px dashed rgba(56,56,56,.72);border-radius:2px;margin-top:10px}.local-font-panel .file-button{position:absolute;top:0;left:0;right:0;bottom:0;opacity:0;z-index:3;cursor:pointer;width:100%}.local-font-panel>div{height:100%;cursor:pointer;text-align:center;line-height:50px}.modal-download-panel .preview-panel{box-shadow:inset -14px -14px 29px rgba(2,30,67,.07),inset 14px 14px 40px rgba(2,30,67,.07),inset -7px -7px 40px rgba(2,30,67,.05),inset 7px 7px 40px rgba(2,30,67,.05);background-size:contain;background-repeat:no-repeat;background-position:50%;width:300px;height:300px}.modal-download-panel .opt-panel{text-align:left;font-size:12px;position:relative;margin-top:9px}.modal-download-panel .opt-panel>span{margin-left:20px;margin-bottom:5px;display:inline-block;margin-top:21px}.modal-download-panel .opt-panel>span>input{margin-right:5px}.modal-download-panel .opt-panel .gelatin-btn{float:right;margin-right:20px;padding-left:50px;padding-right:50px}.shop-template-list{text-align:center}.shop-template-list li{display:inline-block}.shop-template-list li>.item-image{width:90px;height:90px;background-size:contain;cursor:pointer;margin:4px;background-repeat:no-repeat;background-position:50%;background-color:#fff}.shop-template-list .null-data{margin-top:50px}.shop-template-list .null-data .icon-meiyougengduo{font-size:50px;margin-bottom:15px;display:inline-block}.shop-template-list .null-data>div{line-height:20px}#dataset_easy .easy-input-panel{position:absolute;left:0;right:0;bottom:0;top:100px}#dataset_easy .easy-input-panel #jieba-easy-input-list{width:98%;height:100%;border:0;outline:none;font-size:14px;padding-left:2%}#dataset_easy .tip-btn-panel .tips{margin-top:10px;text-align:left}#dataset_easy .tip-btn-panel .check-panel{float:left}#dataset_easy .tip-btn-panel .gelatin-btn{float:right}#hd_hb,#modal_hb_panel,#sub_nav_trigger_invite_list{display:none!important}', ""]),
        t.locals = {
            formLoading: "formLoading",
            "loader-panel": "loader-panel",
            iconfont: "iconfont",
            rotating: "rotating",
            "icon-load": "icon-load",
            "app-body": "app-body",
            main: "main",
            "canvas-container": "canvas-container",
            canvas: "canvas",
            transparent: "transparent",
            "transparent-bck": "transparent-bck",
            "config-item": "config-item",
            "item-label": "item-label",
            "slider-panel": "slider-panel",
            "slider-item": "slider-item",
            "pick-color-item": "pick-color-item",
            "radio-panel": "radio-panel",
            "pay-panel": "pay-panel",
            text: "text",
            active: "active",
            nav: "nav",
            logo: "logo",
            "nav-wrapper": "nav-wrapper",
            "nav-top": "nav-top",
            "sub-nav-note": "sub-nav-note",
            "sub-nav-yq": "sub-nav-yq",
            "icon-title": "icon-title",
            "nav-bottom": "nav-bottom",
            "login-status": "login-status",
            "login-panel": "login-panel",
            "over-panel": "over-panel",
            "over-status": "over-status",
            "nav-bottom-item": "nav-bottom-item",
            toolbar: "toolbar",
            "btn-reload-panel": "btn-reload-panel",
            loading: "loading",
            "toolbar-show": "toolbar-show",
            "toolbar-list": "toolbar-list",
            "input-item": "input-item",
            "input-normal": "input-normal",
            dataset_rule: "dataset_rule",
            "tabs-panel": "tabs-panel",
            "scrollarea-content": "scrollarea-content",
            "textarea-panel": "textarea-panel",
            "content-table-panel": "content-table-panel",
            "input-list": "input-list",
            "jieba-input-list": "jieba-input-list",
            dataset_jiebasplit: "dataset_jiebasplit",
            "gelatin-btn": "gelatin-btn",
            "nav-container": "nav-container",
            "tabs-container": "tabs-container",
            "template-image-list": "template-image-list",
            "tool-panel-item": "tool-panel-item",
            "tool-header": "tool-header",
            "title-drop": "title-drop",
            "title-drop-active": "title-drop-active",
            "tool-panel-text": "tool-panel-text",
            "tool-item": "tool-item",
            "sticker-group": "sticker-group",
            "sticker-item": "sticker-item",
            "tool-panel-component": "tool-panel-component",
            "table-handle-panel": "table-handle-panel",
            "icon-sort": "icon-sort",
            "input-text": "input-text",
            input: "input",
            "family-panel": "family-panel",
            selectric: "selectric",
            label: "label",
            "selectric-scroll": "selectric-scroll",
            "font-img": "font-img",
            "font-text": "font-text",
            "font-vip": "font-vip",
            "font-loading": "font-loading",
            "loading-item": "loading-item",
            "item-like": "item-like",
            "none-data": "none-data",
            "icon-meiyougengduo": "icon-meiyougengduo",
            "page-list": "page-list",
            "icon-unfold": "icon-unfold",
            "active-loading": "active-loading",
            list_item_base_line: "list_item_base_line",
            next: "next",
            "icon-qunawanvip": "icon-qunawanvip",
            "toolbar-color-panel": "toolbar-color-panel",
            "image-add-panel": "image-add-panel",
            "import-box": "import-box",
            "file-button": "file-button",
            "ResourceImage-body": "ResourceImage-body",
            "ResourceImage-item": "ResourceImage-item",
            "ResourceImage-deleteIcon": "ResourceImage-deleteIcon",
            "vip-notifit-panel": "vip-notifit-panel",
            ts: "ts",
            "modal-login-panel": "modal-login-panel",
            "top-info": "top-info",
            title: "title",
            "left-vip": "left-vip",
            "form-panel": "form-panel",
            "login-input": "login-input",
            "reg-btn-0": "reg-btn-0",
            "verify-input": "verify-input",
            "send-verify-btn": "send-verify-btn",
            "verify-loading": "verify-loading",
            form_login_code: "form_login_code",
            "reg-form": "reg-form",
            "name-input": "name-input",
            "none-info": "none-info",
            "other-login": "other-login",
            user_other_login: "user_other_login",
            "loading-panel": "loading-panel",
            yqPanel: "yqPanel",
            yqvalue: "yqvalue",
            "chongzhi-panel": "chongzhi-panel",
            "friend-panel": "friend-panel",
            left_panel_data: "left_panel_data",
            "data-select-panel": "data-select-panel",
            "color-btn-panel": "color-btn-panel",
            btn_order_color: "btn_order_color",
            button: "button",
            "select-theme": "select-theme",
            "selectric-items": "selectric-items",
            "input-panel": "input-panel",
            "input-color-item": "input-color-item",
            "color-group-item": "color-group-item",
            left_panel_tips: "left_panel_tips",
            content: "content",
            way: "way",
            att: "att",
            "background-transparent": "background-transparent",
            expect_word_panel: "expect_word_panel",
            "main-type": "main-type",
            "child-type-list": "child-type-list",
            "btn-panel": "btn-panel",
            "content-list": "content-list",
            "modal-act-panel": "modal-act-panel",
            "preview-image": "preview-image",
            "image-over": "image-over",
            "step-list": "step-list",
            sl: "sl",
            "right-panel": "right-panel",
            tips: "tips",
            "active-hb": "active-hb",
            "shake-little": "shake-little",
            left_panel_invite_list: "left_panel_invite_list",
            "id-tips": "id-tips",
            "show-panel": "show-panel",
            block: "block",
            "add-panel": "add-panel",
            invite_btn_show_add_panel: "invite_btn_show_add_panel",
            invite_btn_add_record: "invite_btn_add_record",
            "load-panel": "load-panel",
            "tblist-panel": "tblist-panel",
            tblist: "tblist",
            "none-panel": "none-panel",
            modal_hd_over: "modal_hd_over",
            act_btn_kaitong: "act_btn_kaitong",
            "rotate-picker-item": "rotate-picker-item",
            "float-drop-color-pick-panel": "float-drop-color-pick-panel",
            collorpicker_2: "collorpicker_2",
            mobileSupport: "mobileSupport",
            "tip-info": "tip-info",
            "local-font-panel": "local-font-panel",
            "modal-download-panel": "modal-download-panel",
            "preview-panel": "preview-panel",
            "opt-panel": "opt-panel",
            "shop-template-list": "shop-template-list",
            "item-image": "item-image",
            "null-data": "null-data",
            dataset_easy: "dataset_easy",
            "easy-input-panel": "easy-input-panel",
            "jieba-easy-input-list": "jieba-easy-input-list",
            "tip-btn-panel": "tip-btn-panel",
            "check-panel": "check-panel",
            modal_hb_panel: "modal_hb_panel",
            hd_hb: "hd_hb",
            sub_nav_trigger_invite_list: "sub_nav_trigger_invite_list"
        }
}, function (e, t) {
}, , function (e, t) {
}, , function (e, t) {
}, , function (e, t, n) {
    (function (e) {
        !function (e, t, n, a) {
            "use strict";
            var i = function () {
                return this.init.apply(this, arguments)
            };
            i.prototype = {
                defaults: {
                    onstatechange: function () {
                    },
                    ondragend: function () {
                    },
                    onbarclicked: function () {
                    },
                    isRange: !1,
                    showLabels: !0,
                    showScale: !0,
                    step: 1,
                    format: "%s",
                    theme: "theme-green",
                    width: 300,
                    disable: !1,
                    snap: !1
                },
                template: '<div class="slider-container">\t\t\t\t<div class="back-bar">\t                <div class="selected-bar"></div>\t                <div class="pointer low"></div><div class="pointer-label low">123456</div>\t                <div class="pointer high"></div><div class="pointer-label high">456789</div>\t                <div class="clickable-dummy"></div>\t            </div>\t            <div class="scale"></div>\t\t\t</div>',
                init: function (t, n) {
                    this.options = e.extend({}, this.defaults, n), this.inputNode = e(t), this.options.value = this.inputNode.val() || (this.options.isRange ? this.options.from + "," + this.options.from : "" + this.options.from), this.domNode = e(this.template), this.domNode.addClass(this.options.theme), this.inputNode.after(this.domNode), this.domNode.on("change", this.onChange), this.pointers = e(".pointer", this.domNode), this.lowPointer = this.pointers.first(), this.highPointer = this.pointers.last(), this.labels = e(".pointer-label", this.domNode), this.lowLabel = this.labels.first(), this.highLabel = this.labels.last(), this.scale = e(".scale", this.domNode), this.bar = e(".selected-bar", this.domNode), this.clickableBar = this.domNode.find(".clickable-dummy"), this.interval = this.options.to - this.options.from, this.render()
                },
                render: function () {
                    return 0 !== this.inputNode.width() || this.options.width ? (this.options.width = this.options.width || this.inputNode.width(), this.domNode.width(this.options.width), this.inputNode.hide(), this.isSingle() && (this.lowPointer.hide(), this.lowLabel.hide()), this.options.showLabels || this.labels.hide(), this.attachEvents(), this.options.showScale && this.renderScale(), void this.setValue(this.options.value)) : void console.log("jRange : no width found, returning")
                },
                isSingle: function () {
                    return "number" == typeof this.options.value || this.options.value.indexOf(",") === -1 && !this.options.isRange
                },
                attachEvents: function () {
                    this.clickableBar.click(e.proxy(this.barClicked, this)), this.pointers.on("mousedown touchstart", e.proxy(this.onDragStart, this)), this.pointers.bind("dragstart", function (e) {
                        e.preventDefault()
                    })
                },
                onDragStart: function (t) {
                    if (!(this.options.disable || "mousedown" === t.type && 1 !== t.which)) {
                        t.stopPropagation(), t.preventDefault();
                        var a = e(t.target);
                        this.pointers.removeClass("last-active"), a.addClass("focused last-active"), this[(a.hasClass("low") ? "low" : "high") + "Label"].addClass("focused"), e(n).on("mousemove.slider touchmove.slider", e.proxy(this.onDrag, this, a)), e(n).on("mouseup.slider touchend.slider touchcancel.slider", e.proxy(this.onDragEnd, this))
                    }
                },
                onDrag: function (e, t) {
                    t.stopPropagation(), t.preventDefault(), t.originalEvent.touches && t.originalEvent.touches.length ? t = t.originalEvent.touches[0] : t.originalEvent.changedTouches && t.originalEvent.changedTouches.length && (t = t.originalEvent.changedTouches[0]);
                    var n = t.clientX - this.domNode.offset().left;
                    this.domNode.trigger("change", [this, e, n])
                },
                onDragEnd: function (t) {
                    this.pointers.removeClass("focused").trigger("rangeslideend"), this.labels.removeClass("focused"), e(n).off(".slider"), this.options.ondragend.call(this, this.options.value)
                },
                barClicked: function (e) {
                    if (!this.options.disable) {
                        var t = e.pageX - this.clickableBar.offset().left;
                        if (this.isSingle())this.setPosition(this.pointers.last(), t, !0, !0); else {
                            var n, a = Math.abs(parseFloat(this.pointers.first().css("left"), 10)), i = this.pointers.first().width() / 2, o = Math.abs(parseFloat(this.pointers.last().css("left"), 10)), r = this.pointers.first().width() / 2, s = Math.abs(a - t + i), l = Math.abs(o - t + r);
                            n = s == l ? t < a ? this.pointers.first() : this.pointers.last() : s < l ? this.pointers.first() : this.pointers.last(), this.setPosition(n, t, !0, !0)
                        }
                        this.options.onbarclicked.call(this, this.options.value)
                    }
                },
                onChange: function (e, t, n, a) {
                    var i, o;
                    i = 0, o = t.domNode.width(), t.isSingle() || (i = n.hasClass("high") ? parseFloat(t.lowPointer.css("left")) + t.lowPointer.width() / 2 : 0, o = n.hasClass("low") ? parseFloat(t.highPointer.css("left")) + t.highPointer.width() / 2 : t.domNode.width());
                    var r = Math.min(Math.max(a, i), o);
                    t.setPosition(n, r, !0)
                },
                setPosition: function (e, t, n, a) {
                    var i, o, r = parseFloat(this.lowPointer.css("left")), s = parseFloat(this.highPointer.css("left")) || 0, l = this.highPointer.width() / 2;
                    if (n || (t = this.prcToPx(t)), this.options.snap) {
                        var d = this.correctPositionForSnap(t);
                        if (d === -1)return;
                        t = d
                    }
                    e[0] === this.highPointer[0] ? s = Math.round(t - l) : r = Math.round(t - l), e[a ? "animate" : "css"]({left: Math.round(t - l)}), this.isSingle() ? i = 0 : (i = r + l, o = s + l);
                    var c = Math.round(s + l - i);
                    this.bar[a ? "animate" : "css"]({
                        width: Math.abs(c),
                        left: c > 0 ? i : i + c
                    }), this.showPointerValue(e, t, a), this.isReadonly()
                },
                correctPositionForSnap: function (e) {
                    var t = this.positionToValue(e) - this.options.from, n = this.options.width / (this.interval / this.options.step), a = t / this.options.step * n;
                    return e <= a + n / 2 && e >= a - n / 2 ? a : -1
                },
                setValue: function (e) {
                    var t = e.toString().split(",");
                    t[0] = Math.min(Math.max(t[0], this.options.from), this.options.to) + "", t.length > 1 && (t[1] = Math.min(Math.max(t[1], this.options.from), this.options.to) + ""), this.options.value = e;
                    var n = this.valuesToPrc(2 === t.length ? t : [0, t[0]]);
                    this.isSingle() ? this.setPosition(this.highPointer, n[1]) : (this.setPosition(this.lowPointer, n[0]), this.setPosition(this.highPointer, n[1]))
                },
                renderScale: function () {
                    for (var t = this.options.scale || [this.options.from, this.options.to], n = Math.round(100 / (t.length - 1) * 10) / 10, a = "", i = 0; i < t.length; i++)a += '<span style="left: ' + i * n + '%">' + ("|" != t[i] ? "<ins>" + t[i] + "</ins>" : "") + "</span>";
                    this.scale.html(a), e("ins", this.scale).each(function () {
                        e(this).css({marginLeft: -e(this).outerWidth() / 2})
                    })
                },
                getBarWidth: function () {
                    var e = this.options.value.split(",");
                    return e.length > 1 ? parseFloat(e[1]) - parseFloat(e[0]) : parseFloat(e[0])
                },
                showPointerValue: function (t, n, i) {
                    var o, r = e(".pointer-label", this.domNode)[t.hasClass("low") ? "first" : "last"](), s = this.positionToValue(n);
                    if (e.isFunction(this.options.format)) {
                        var l = this.isSingle() ? a : t.hasClass("low") ? "low" : "high";
                        o = this.options.format(s, l)
                    } else o = this.options.format.replace("%s", s);
                    var d = r.html(o).width(), c = n - d / 2;
                    c = Math.min(Math.max(c, 0), this.options.width - d), r[i ? "animate" : "css"]({left: c}), this.setInputValue(t, s)
                },
                valuesToPrc: function (e) {
                    var t = 100 * (parseFloat(e[0]) - parseFloat(this.options.from)) / this.interval, n = 100 * (parseFloat(e[1]) - parseFloat(this.options.from)) / this.interval;
                    return [t, n]
                },
                prcToPx: function (e) {
                    return this.domNode.width() * e / 100
                },
                isDecimal: function () {
                    return (this.options.value + this.options.from + this.options.to).indexOf(".") !== -1
                },
                positionToValue: function (e) {
                    var t = e / this.domNode.width() * this.interval;
                    if (t = parseFloat(t, 10) + parseFloat(this.options.from, 10), this.isDecimal()) {
                        var n = Math.round(Math.round(t / this.options.step) * this.options.step * 100) / 100;
                        if (0 !== n)for (n = "" + n, n.indexOf(".") === -1 && (n += "."); n.length - n.indexOf(".") < 3;)n += "0"; else n = "0.00";
                        return n
                    }
                    return Math.round(t / this.options.step) * this.options.step
                },
                setInputValue: function (e, t) {
                    if (this.isSingle())this.options.value = t.toString(); else {
                        var n = this.options.value.split(",");
                        e.hasClass("low") ? this.options.value = t + "," + n[1] : this.options.value = n[0] + "," + t
                    }
                    this.inputNode.val() !== this.options.value && (this.inputNode.val(this.options.value).trigger("change"), this.options.onstatechange.call(this, this.options.value))
                },
                getValue: function () {
                    return this.options.value
                },
                getOptions: function () {
                    return this.options
                },
                getRange: function () {
                    return this.options.from + "," + this.options.to
                },
                isReadonly: function () {
                    this.domNode.toggleClass("slider-readonly", this.options.disable)
                },
                disable: function () {
                    this.options.disable = !0, this.isReadonly()
                },
                enable: function () {
                    this.options.disable = !1, this.isReadonly()
                },
                toggleDisable: function () {
                    this.options.disable = !this.options.disable, this.isReadonly()
                },
                updateRange: function (e, t) {
                    var n = e.toString().split(",");
                    this.interval = parseInt(n[1]) - parseInt(n[0]), t ? this.setValue(t) : this.setValue(this.getValue())
                }
            };
            var o = "jRange";
            e.fn[o] = function (n) {
                var a, r = arguments;
                return this.each(function () {
                    var s = e(this), l = e.data(this, "plugin_" + o), d = "object" == typeof n && n;
                    l || (s.data("plugin_" + o, l = new i(this, d)), e(t).resize(function () {
                        l.setValue(l.getValue())
                    })), "string" == typeof n && (a = l[n].apply(l, Array.prototype.slice.call(r, 1)))
                }), a || this
            }
        }(e, window, document)
    }).call(t, n(1))
}, function (e, t, n) {
    (function (e) {
        !function (e) {
            var t = function () {
                var t = '<div class="colpick"><div class="colpick_color"><div class="colpick_color_overlay1"><div class="colpick_selector_outer"></div></div></div><div class="colpick_hue"><div class="colpick_hue_arrs"><div class="colpick_hue_larr"></div><div class="colpick_hue_rarr"></div></div></div><div class="colpick_new_color"></div><div class="colpick_current_color"></div><div class="colpick_hex_field"><div class="colpick_field_letter">#</div><input type="text" maxlength="6" size="6" /></div><div class="colpick_rgb_r colpick_field"><div class="colpick_field_letter">R</div><input type="text" maxlength="3" size="3" /><div class="colpick_field_arrs"><div class="colpick_field_uarr"></div><div class="colpick_field_darr"></div></div></div><div class="colpick_rgb_g colpick_field"><div class="colpick_field_letter">G</div><input type="text" maxlength="3" size="3" /><div class="colpick_field_arrs"><div class="colpick_field_uarr"></div><div class="colpick_field_darr"></div></div></div><div class="colpick_rgb_b colpick_field"><div class="colpick_field_letter">B</div><input type="text" maxlength="3" size="3" /><div class="colpick_field_arrs"><div class="colpick_field_uarr"></div><div class="colpick_field_darr"></div></div></div><div class="colpick_hsb_h colpick_field"><div class="colpick_field_letter">H</div><input type="text" maxlength="3" size="3" /><div class="colpick_field_arrs"><div class="colpick_field_uarr"></div><div class="colpick_field_darr"></div></div></div><div class="colpick_hsb_s colpick_field"><div class="colpick_field_letter">S</div><input type="text" maxlength="3" size="3" /><div class="colpick_field_arrs"><div class="colpick_field_uarr"></div><div class="colpick_field_darr"></div></div></div><div class="colpick_hsb_b colpick_field"><div class="colpick_field_letter">B</div><input type="text" maxlength="3" size="3" /><div class="colpick_field_arrs"><div class="colpick_field_uarr"></div><div class="colpick_field_darr"></div></div></div><div class="colpick_submit"></div></div>', n = {
                    showEvent: "click",
                    onShow: function () {
                    },
                    onBeforeShow: function () {
                    },
                    onHide: function () {
                    },
                    onChange: function () {
                    },
                    onSubmit: function () {
                    },
                    colorScheme: "light",
                    color: "3289c7",
                    livePreview: !0,
                    flat: !1,
                    layout: "full",
                    submit: 1,
                    submitText: "OK",
                    height: 156
                }, r = function (t, n) {
                    var a = o(t);
                    e(n).data("colpick").fields.eq(1).val(a.r).end().eq(2).val(a.g).end().eq(3).val(a.b).end()
                }, l = function (t, n) {
                    e(n).data("colpick").fields.eq(4).val(Math.round(t.h)).end().eq(5).val(Math.round(t.s)).end().eq(6).val(Math.round(t.b)).end()
                }, d = function (t, n) {
                    e(n).data("colpick").fields.eq(0).val(s(t))
                }, c = function (t, n) {
                    e(n).data("colpick").selector.css("backgroundColor", "#" + s({
                            h: t.h,
                            s: 100,
                            b: 100
                        })), e(n).data("colpick").selectorIndic.css({
                        left: parseInt(e(n).data("colpick").height * t.s / 100, 10),
                        top: parseInt(e(n).data("colpick").height * (100 - t.b) / 100, 10)
                    })
                }, u = function (t, n) {
                    e(n).data("colpick").hue.css("top", parseInt(e(n).data("colpick").height - e(n).data("colpick").height * t.h / 360, 10))
                }, m = function (t, n) {
                    e(n).data("colpick").currentColor.css("backgroundColor", "#" + s(t))
                }, _ = function (t, n) {
                    e(n).data("colpick").newColor.css("backgroundColor", "#" + s(t))
                }, p = function (t) {
                    var n, m = e(this).parent().parent();
                    this.parentNode.className.indexOf("_hex") > 0 ? (m.data("colpick").color = n = a(j(this.value)), r(n, m.get(0)), l(n, m.get(0))) : this.parentNode.className.indexOf("_hsb") > 0 ? (m.data("colpick").color = n = z({
                        h: parseInt(m.data("colpick").fields.eq(4).val(), 10),
                        s: parseInt(m.data("colpick").fields.eq(5).val(), 10),
                        b: parseInt(m.data("colpick").fields.eq(6).val(), 10)
                    }), r(n, m.get(0)), d(n, m.get(0))) : (m.data("colpick").color = n = i(H({
                        r: parseInt(m.data("colpick").fields.eq(1).val(), 10),
                        g: parseInt(m.data("colpick").fields.eq(2).val(), 10),
                        b: parseInt(m.data("colpick").fields.eq(3).val(), 10)
                    })), d(n, m.get(0)), l(n, m.get(0))), c(n, m.get(0)), u(n, m.get(0)), _(n, m.get(0)), m.data("colpick").onChange.apply(m.parent(), [n, s(n), o(n), m.data("colpick").el, 0])
                }, h = function (t) {
                    e(this).parent().removeClass("colpick_focus")
                }, f = function () {
                    e(this).parent().parent().data("colpick").fields.parent().removeClass("colpick_focus"), e(this).parent().addClass("colpick_focus")
                }, g = function (t) {
                    t.preventDefault ? t.preventDefault() : t.returnValue = !1;
                    var n = e(this).parent().find("input").focus(), a = {
                        el: e(this).parent().addClass("colpick_slider"),
                        max: this.parentNode.className.indexOf("_hsb_h") > 0 ? 360 : this.parentNode.className.indexOf("_hsb") > 0 ? 100 : 255,
                        y: t.pageY,
                        field: n,
                        val: parseInt(n.val(), 10),
                        preview: e(this).parent().parent().data("colpick").livePreview
                    };
                    e(document).mouseup(a, v), e(document).mousemove(a, y)
                }, y = function (e) {
                    return e.data.field.val(Math.max(0, Math.min(e.data.max, parseInt(e.data.val - e.pageY + e.data.y, 10)))), e.data.preview && p.apply(e.data.field.get(0), [!0]), !1
                }, v = function (t) {
                    return p.apply(t.data.field.get(0), [!0]), t.data.el.removeClass("colpick_slider").find("input").focus(), e(document).off("mouseup", v), e(document).off("mousemove", y), !1
                }, b = function (t) {
                    t.preventDefault ? t.preventDefault() : t.returnValue = !1;
                    var n = {cal: e(this).parent(), y: e(this).offset().top};
                    e(document).on("mouseup touchend", n, M), e(document).on("mousemove touchmove", n, w);
                    var a = "touchstart" == t.type ? t.originalEvent.changedTouches[0].pageY : t.pageY;
                    return p.apply(n.cal.data("colpick").fields.eq(4).val(parseInt(360 * (n.cal.data("colpick").height - (a - n.y)) / n.cal.data("colpick").height, 10)).get(0), [n.cal.data("colpick").livePreview]), !1
                }, w = function (e) {
                    var t = "touchmove" == e.type ? e.originalEvent.changedTouches[0].pageY : e.pageY;
                    return p.apply(e.data.cal.data("colpick").fields.eq(4).val(parseInt(360 * (e.data.cal.data("colpick").height - Math.max(0, Math.min(e.data.cal.data("colpick").height, t - e.data.y))) / e.data.cal.data("colpick").height, 10)).get(0), [e.data.preview]), !1
                }, M = function (t) {
                    return r(t.data.cal.data("colpick").color, t.data.cal.get(0)), d(t.data.cal.data("colpick").color, t.data.cal.get(0)), e(document).off("mouseup touchend", M), e(document).off("mousemove touchmove", w), !1
                }, k = function (t) {
                    t.preventDefault ? t.preventDefault() : t.returnValue = !1;
                    var n = {cal: e(this).parent(), pos: e(this).offset()};
                    n.preview = n.cal.data("colpick").livePreview, e(document).on("mouseup touchend", n, x), e(document).on("mousemove touchmove", n, L);
                    var a, i;
                    return "touchstart" == t.type ? (a = t.originalEvent.changedTouches[0].pageX, i = t.originalEvent.changedTouches[0].pageY) : (a = t.pageX, i = t.pageY), p.apply(n.cal.data("colpick").fields.eq(6).val(parseInt(100 * (n.cal.data("colpick").height - (i - n.pos.top)) / n.cal.data("colpick").height, 10)).end().eq(5).val(parseInt(100 * (a - n.pos.left) / n.cal.data("colpick").height, 10)).get(0), [n.preview]), !1
                }, L = function (e) {
                    var t, n;
                    return "touchmove" == e.type ? (t = e.originalEvent.changedTouches[0].pageX, n = e.originalEvent.changedTouches[0].pageY) : (t = e.pageX, n = e.pageY), p.apply(e.data.cal.data("colpick").fields.eq(6).val(parseInt(100 * (e.data.cal.data("colpick").height - Math.max(0, Math.min(e.data.cal.data("colpick").height, n - e.data.pos.top))) / e.data.cal.data("colpick").height, 10)).end().eq(5).val(parseInt(100 * Math.max(0, Math.min(e.data.cal.data("colpick").height, t - e.data.pos.left)) / e.data.cal.data("colpick").height, 10)).get(0), [e.data.preview]), !1
                }, x = function (t) {
                    return r(t.data.cal.data("colpick").color, t.data.cal.get(0)), d(t.data.cal.data("colpick").color, t.data.cal.get(0)), e(document).off("mouseup touchend", x), e(document).off("mousemove touchmove", L), !1
                }, Y = function (t) {
                    var n = e(this).parent(), a = n.data("colpick").color;
                    n.data("colpick").origColor = a, m(a, n.get(0)), n.data("colpick").onSubmit(a, s(a), o(a), n.data("colpick").el)
                }, D = function (t) {
                    t.stopPropagation();
                    var n = e("#" + e(this).data("colpickId"));
                    n.data("colpick").onBeforeShow.apply(this, [n.get(0)]);
                    var a = e(this).offset(), i = a.top + this.offsetHeight, o = a.left, r = S(), s = n.width();
                    o + s > r.l + r.w && (o -= s), n.css({
                        left: o + "px",
                        top: i + "px"
                    }), 0 != n.data("colpick").onShow.apply(this, [n.get(0)]) && n.show(), e("html").mousedown({cal: n}, T), n.mousedown(function (e) {
                        e.stopPropagation()
                    })
                }, T = function (t) {
                    0 != t.data.cal.data("colpick").onHide.apply(this, [t.data.cal.get(0)]) && t.data.cal.hide(), e("html").off("mousedown", T)
                }, S = function () {
                    var e = "CSS1Compat" == document.compatMode;
                    return {
                        l: window.pageXOffset || (e ? document.documentElement.scrollLeft : document.body.scrollLeft),
                        w: window.innerWidth || (e ? document.documentElement.clientWidth : document.body.clientWidth)
                    }
                }, z = function (e) {
                    return {
                        h: Math.min(360, Math.max(0, e.h)),
                        s: Math.min(100, Math.max(0, e.s)),
                        b: Math.min(100, Math.max(0, e.b))
                    }
                }, H = function (e) {
                    return {
                        r: Math.min(255, Math.max(0, e.r)),
                        g: Math.min(255, Math.max(0, e.g)),
                        b: Math.min(255, Math.max(0, e.b))
                    }
                }, j = function (e) {
                    var t = 6 - e.length;
                    if (t > 0) {
                        for (var n = [], a = 0; a < t; a++)n.push("0");
                        n.push(e), e = n.join("")
                    }
                    return e
                }, F = function () {
                    var e = 0;
                    return function () {
                        return e += 1
                    }
                }(), P = function () {
                    var t = e(this).parent(), n = t.data("colpick").origColor;
                    t.data("colpick").color = n, r(n, t.get(0)), d(n, t.get(0)), l(n, t.get(0)), c(n, t.get(0)), u(n, t.get(0)), _(n, t.get(0))
                };
                return {
                    init: function (o) {
                        if (o = e.extend({}, n, o || {}), "string" == typeof o.color)o.color = a(o.color); else if (void 0 != o.color.r && void 0 != o.color.g && void 0 != o.color.b)o.color = i(o.color); else {
                            if (void 0 == o.color.h || void 0 == o.color.s || void 0 == o.color.b)return this;
                            o.color = z(o.color)
                        }
                        return this.each(function () {
                            if (!e(this).data("colpickId")) {
                                var n = e.extend({}, o);
                                n.origColor = o.color;
                                var a = "collorpicker_" + F();
                                e(this).data("colpickId", a);
                                var i = e(t).attr("id", a);
                                i.addClass("colpick_" + n.layout + (n.submit ? "" : " colpick_" + n.layout + "_ns")), "light" != n.colorScheme && i.addClass("colpick_" + n.colorScheme), i.find(".colpick_submit").html(n.submitText).click(Y), n.fields = i.find("input").change(p).blur(h).focus(f), i.find(".colpick_field_arrs").mousedown(g).end().find(".colpick_current_color").click(P), n.selector = i.find(".colpick_color").on("mousedown touchstart", k), n.selectorIndic = n.selector.find(".colpick_selector_outer"), n.el = this, n.hue = i.find(".colpick_hue_arrs");
                                var s = n.hue.parent(), y = navigator.userAgent.toLowerCase(), v = "Microsoft Internet Explorer" === navigator.appName, w = v ? parseFloat(y.match(/msie ([0-9]{1,}[\.0-9]{0,})/)[1]) : 0, M = v && w < 10, L = ["#ff0000", "#ff0080", "#ff00ff", "#8000ff", "#0000ff", "#0080ff", "#00ffff", "#00ff80", "#00ff00", "#80ff00", "#ffff00", "#ff8000", "#ff0000"];
                                if (M) {
                                    var x, T;
                                    for (x = 0; x <= 11; x++)T = e("<div></div>").attr("style", "height:8.333333%; filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=" + L[x] + ", endColorstr=" + L[x + 1] + '); -ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=' + L[x] + ", endColorstr=" + L[x + 1] + ')";'), s.append(T)
                                } else {
                                    var S = L.join(",");
                                    s.attr("style", "background:-webkit-linear-gradient(top," + S + "); background: -o-linear-gradient(top," + S + "); background: -ms-linear-gradient(top," + S + "); background:-moz-linear-gradient(top," + S + "); -webkit-linear-gradient(top," + S + "); background:linear-gradient(to bottom," + S + "); ")
                                }
                                i.find(".colpick_hue").on("mousedown touchstart", b), n.newColor = i.find(".colpick_new_color"), n.currentColor = i.find(".colpick_current_color"), i.data("colpick", n), r(n.color, i.get(0)), l(n.color, i.get(0)), d(n.color, i.get(0)), u(n.color, i.get(0)), c(n.color, i.get(0)), m(n.color, i.get(0)), _(n.color, i.get(0)), n.flat ? (i.appendTo(this).show(), i.css({
                                    position: "relative",
                                    display: "block"
                                })) : (i.appendTo(document.body), e(this).on(n.showEvent, D), i.css({position: "absolute"}))
                            }
                        })
                    }, showPicker: function () {
                        return this.each(function () {
                            e(this).data("colpickId") && D.apply(this)
                        })
                    }, hidePicker: function () {
                        return this.each(function () {
                            e(this).data("colpickId") && e("#" + e(this).data("colpickId")).hide()
                        })
                    }, setColor: function (t, n) {
                        if (n = "undefined" == typeof n ? 1 : n, "string" == typeof t)t = a(t); else if (void 0 != t.r && void 0 != t.g && void 0 != t.b)t = i(t); else {
                            if (void 0 == t.h || void 0 == t.s || void 0 == t.b)return this;
                            t = z(t)
                        }
                        return this.each(function () {
                            if (e(this).data("colpickId")) {
                                var a = e("#" + e(this).data("colpickId"));
                                a.data("colpick").color = t, a.data("colpick").origColor = t, r(t, a.get(0)), l(t, a.get(0)), d(t, a.get(0)), u(t, a.get(0)), c(t, a.get(0)), _(t, a.get(0)), a.data("colpick").onChange.apply(a.parent(), [t, s(t), o(t), a.data("colpick").el, 1]), n && m(t, a.get(0))
                            }
                        })
                    }
                }
            }(), n = function (e) {
                return e = parseInt(e.indexOf("#") > -1 ? e.substring(1) : e, 16), {
                    r: e >> 16,
                    g: (65280 & e) >> 8,
                    b: 255 & e
                }
            }, a = function (e) {
                return i(n(e))
            }, i = function (e) {
                var t = {h: 0, s: 0, b: 0}, n = Math.min(e.r, e.g, e.b), a = Math.max(e.r, e.g, e.b), i = a - n;
                return t.b = a, t.s = 0 != a ? 255 * i / a : 0, 0 != t.s ? e.r == a ? t.h = (e.g - e.b) / i : e.g == a ? t.h = 2 + (e.b - e.r) / i : t.h = 4 + (e.r - e.g) / i : t.h = -1, t.h *= 60, t.h < 0 && (t.h += 360), t.s *= 100 / 255, t.b *= 100 / 255, t
            }, o = function (e) {
                var t = {}, n = e.h, a = 255 * e.s / 100, i = 255 * e.b / 100;
                if (0 == a)t.r = t.g = t.b = i; else {
                    var o = i, r = (255 - a) * i / 255, s = (o - r) * (n % 60) / 60;
                    360 == n && (n = 0), n < 60 ? (t.r = o, t.b = r, t.g = r + s) : n < 120 ? (t.g = o, t.b = r, t.r = o - s) : n < 180 ? (t.g = o, t.r = r, t.b = r + s) : n < 240 ? (t.b = o, t.r = r, t.g = o - s) : n < 300 ? (t.b = o, t.g = r, t.r = r + s) : n < 360 ? (t.r = o, t.g = r, t.b = o - s) : (t.r = 0, t.g = 0, t.b = 0)
                }
                return {r: Math.round(t.r), g: Math.round(t.g), b: Math.round(t.b)}
            }, r = function (t) {
                var n = [t.r.toString(16), t.g.toString(16), t.b.toString(16)];
                return e.each(n, function (e, t) {
                    1 == t.length && (n[e] = "0" + t)
                }), n.join("")
            }, s = function (e) {
                return r(o(e))
            };
            e.fn.extend({
                colpick: t.init,
                colpickHide: t.hidePicker,
                colpickShow: t.showPicker,
                colpickSetColor: t.setColor
            }), e.extend({colpick: {rgbToHex: r, rgbToHsb: i, hsbToHex: s, hsbToRgb: o, hexToHsb: a, hexToRgb: n}})
        }(e)
    }).call(t, n(1))
}, function (e, t) {
}, , function (e, t) {
}]);